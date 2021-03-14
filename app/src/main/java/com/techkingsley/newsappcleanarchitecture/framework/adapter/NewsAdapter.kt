package com.techkingsley.newsappcleanarchitecture.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.LayoutNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var adapterDataList: List<Any> = emptyList()

    fun submitList(item: List<Any>) {
        adapterDataList = item
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return adapterDataList.size
    }

    inner class NewsViewHolder(private val viewBinding: LayoutNewsBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(bindingNews: News) {
            with(viewBinding) {
                news = bindingNews
                val requestOptions = RequestOptions().transform(RoundedCorners(50))
                Glide.with(newsImage.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(bindingNews.urlToImage)
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(newsImage)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val viewBinding = LayoutNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(adapterDataList[position] as News)
    }
}




