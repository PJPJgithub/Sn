package com.gm.carcontrolsim.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gm.carcontrolsim.R
// ArticleListAdapter 클래스는 기사 목록을 표시하기 위한 어댑터
class ArticleListAdapter(
    private val context: Context,
    private val headlines: List<String>,
    private val times: List<String>
) : BaseAdapter() {

    override fun getCount(): Int = headlines.size// getCount 메서드는 리스트의 항목 수를 반환

    override fun getItem(position: Int): Any = headlines[position]// getItem 메서드는 주어진 위치의 항목을 반환

    override fun getItemId(position: Int): Long = position.toLong()// getItemId 메서드는 주어진 위치의 항목 ID를 반환

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {// getView 메서드는 주어진 위치의 항목에 대한 뷰를 생성하거나 재사용
        // convertView가 null이면 새로운 뷰를 생성하고, 그렇지 않으면 재사용
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)

        val headlineTextView: TextView = view.findViewById(R.id.headline_text_view)
        val timeTextView: TextView = view.findViewById(R.id.time_text_view)

        headlineTextView.text = headlines[position]
        timeTextView.text = times[position]

        return view
    }

}