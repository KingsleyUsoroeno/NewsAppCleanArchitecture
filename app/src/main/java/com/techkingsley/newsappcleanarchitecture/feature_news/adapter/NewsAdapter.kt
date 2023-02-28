package com.techkingsley.newsappcleanarchitecture.feature_news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.LayoutNewsBinding

class NewsAdapter(private val onNewsBookmarkClicked: (news: News) -> Unit) :
    ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    inner class NewsViewHolder(private val viewBinding: LayoutNewsBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        private val context: Context by lazy { viewBinding.root.context }

        fun bind(news: News) {
            with(viewBinding) {
                this.news = news
                val requestOptions = RequestOptions().transform(RoundedCorners(50))
                Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(news.urlToImage)
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(newsImage)
                bookmarkIcon.setOnClickListener {
                    onNewsBookmarkClicked(news)
                }
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val viewBinding = LayoutNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id && oldItem.category == newItem.category
        }
    }
}





