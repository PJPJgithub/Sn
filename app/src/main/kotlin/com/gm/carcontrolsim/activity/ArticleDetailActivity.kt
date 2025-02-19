package com.gm.carcontrolsim.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gm.carcontrolsim.R
import com.squareup.picasso.Picasso

class ArticleDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val headline = intent.getStringExtra("headline")
        val query = intent.getStringExtra("query")
        val url = intent.getStringExtra("url")
        val imageUrl = intent.getStringExtra("imageUrl")
        val description = intent.getStringExtra("description")
        val source = intent.getStringExtra("source")
        val author = intent.getStringExtra("author")
        val publishedAt = intent.getStringExtra("publishedAt")

        findViewById<TextView>(R.id.headline_text_view).text = headline
        findViewById<TextView>(R.id.query_text_view).text = "Keyword: $query"
        findViewById<TextView>(R.id.url_text_view).text = "URL: \n$url"
        findViewById<TextView>(R.id.description_text_view).text = "Description: \n$description"
        findViewById<TextView>(R.id.source_text_view).text = "Source: $source"
        findViewById<TextView>(R.id.author_text_view).text = "Author: $author"
        findViewById<TextView>(R.id.published_at_text_view).text = "Published At: $publishedAt"

        val imageView = findViewById<ImageView>(R.id.article_image_view)
        if (imageUrl != null) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.loading_image)//로딩 이미지
                .error(R.drawable.symbol_image)//로딩 실패 시
                .into(imageView)
        } else {
            imageView.setImageResource(R.drawable.symbol_image)
        }

        // 뒤로가기 버튼 클릭 리스너 추가
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }
    }
}
