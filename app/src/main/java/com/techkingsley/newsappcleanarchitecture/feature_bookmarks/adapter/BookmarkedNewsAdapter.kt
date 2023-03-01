package com.techkingsley.newsappcleanarchitecture.feature_bookmarks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.databinding.LayoutBookmarkedNewsItemBinding

class BookmarkedNewsAdapter :
    ListAdapter<News, BookmarkedNewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    inner class NewsViewHolder(private val viewBinding: LayoutBookmarkedNewsItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(news: News) {
            viewBinding.apply {
                this.news = news
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val viewBinding = LayoutBookmarkedNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }
    }
}





