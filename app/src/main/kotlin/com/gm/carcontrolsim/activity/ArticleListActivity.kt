package com.gm.carcontrolsim.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.gm.carcontrolsim.R
import com.gm.carcontrolsim.adapter.ArticleListAdapter

class ArticleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

        val listView: ListView = findViewById(R.id.article_list_view)
        val headlines = intent.getStringArrayListExtra("headlines")
        val queries = intent.getStringArrayListExtra("queries")
        val urls = intent.getStringArrayListExtra("urls")
        val times = intent.getStringArrayListExtra("times")
        val descriptions = intent.getStringArrayListExtra("descriptions")
        val sources = intent.getStringArrayListExtra("sources")
        val authors = intent.getStringArrayListExtra("authors")
        val publishedAts = intent.getStringArrayListExtra("publishedAts")
        val imageUrls = intent.getStringArrayListExtra("urlToImages")

        if (headlines != null && queries != null && urls != null && times != null) {
            val adapter = ArticleListAdapter(this, headlines, times)//, queries, urls
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(this, ArticleDetailActivity::class.java).apply {
                    putExtra("headline", headlines[position])
                    putExtra("query", queries[position])
                    putExtra("url", urls[position])
                    putExtra("description", descriptions?.get(position))
                    putExtra("source", sources?.get(position))
                    putExtra("author", authors?.get(position))
                    putExtra("publishedAt", publishedAts?.get(position))
                    putExtra("imageUrl", imageUrls?.get(position))
                }
                startActivity(intent)
            }
        }

        // 뒤로가기 버튼 클릭 리스너 추가
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }
    }
}