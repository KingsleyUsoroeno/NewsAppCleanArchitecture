package com.techkingsley.newsappcleanarchitecture.framework.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.techkingsley.domain.entities.News
import com.techkingsley.newsappcleanarchitecture.R

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var adapterDataList: List<Any> = emptyList()

    fun submitList(item: List<Any>) {
        adapterDataList = item
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return adapterDataList.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newsTitleTextView = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsImage = itemView.findViewById<AppCompatImageView>(R.id.newsImage)
        private val newsDescriptionTextView = itemView.findViewById<AppCompatTextView>(R.id.newsDescription)

        fun bind(news: News) {
            with(news) {
                newsTitleTextView.text = title
                newsDescriptionTextView.text = description
                val requestOptions = RequestOptions().transform(RoundedCorners(50))
                Glide.with(newsImage.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(urlToImage)
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(newsImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(adapterDataList[position] as News)
    }
}




