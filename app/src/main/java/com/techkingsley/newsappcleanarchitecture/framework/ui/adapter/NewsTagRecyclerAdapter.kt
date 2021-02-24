package com.techkingsley.newsappcleanarchitecture.framework.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.techkingsley.newsappcleanarchitecture.R
import java.util.*


class NewsTagRecyclerAdapter(private val items: MutableList<String>, private val onItemClickedListener: OnItemClickedListener) :
    RecyclerView.Adapter<NewsTagRecyclerAdapter.NewsTagViewHolder>() {

    interface OnItemClickedListener {
        fun onNewsTagClicked(tag: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsTagViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.layout_news_tag, parent, false)
        return NewsTagViewHolder(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsTagViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    inner class NewsTagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: AppCompatTextView = view.findViewById(R.id.tagText)
        private val v = view

        fun bind(item: String) {
            textView.text = item
            val rnd = Random()
            val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            v.background.setTint(color)
            v.setOnClickListener { onItemClickedListener.onNewsTagClicked(item) }
        }

    }
}