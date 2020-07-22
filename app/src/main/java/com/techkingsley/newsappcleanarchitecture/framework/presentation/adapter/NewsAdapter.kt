package com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter

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
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.BaseViewHolder<*>>() {

    private var adapterDataList: List<Any> = emptyList()

    companion object {
        private const val TYPE_TECHNOLOGY = 0
        private const val TYPE_TRENDING = 1
        private const val TYPE_POLITICS = 2
        private const val TYPE_MOVIES = 3
    }

    fun setItem(item: List<Any>) {
        adapterDataList = item
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_news, parent, false)

        return when (viewType) {
            TYPE_TECHNOLOGY -> TechnologyNewsViewHolder(view)

            TYPE_TRENDING -> TrendingNewsViewHolder(view)

            TYPE_POLITICS -> PoliticalNewsViewHolder(view)

            TYPE_MOVIES -> MovieNewsNewsViewHolder(view)

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterDataList[position]) {
            is TechnologyNews -> TYPE_TECHNOLOGY
            is TrendingNews -> TYPE_TRENDING
            is PoliticalNews -> TYPE_POLITICS
            is Movies -> TYPE_MOVIES
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    override fun getItemCount(): Int {
        return adapterDataList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        when (holder) {
            is TechnologyNewsViewHolder -> holder.bind(element as TechnologyNews)
            is TrendingNewsViewHolder -> holder.bind(element as TrendingNews)
            is PoliticalNewsViewHolder -> holder.bind(element as PoliticalNews)
            is MovieNewsNewsViewHolder -> holder.bind(element as Movies)
            else -> throw IllegalArgumentException()
        }
    }

    inner class TechnologyNewsViewHolder(itemView: View) : BaseViewHolder<TechnologyNews>(itemView) {
        private val newsTitleTextView = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsImage = itemView.findViewById<AppCompatImageView>(R.id.newsImage)
        private val newsDescriptionTextView = itemView.findViewById<AppCompatTextView>(R.id.newsDescription)

        override fun bind(item: TechnologyNews) {
            newsTitleTextView.text = item.title
            newsDescriptionTextView.text = item.description
            val requestOptions = RequestOptions().transform(RoundedCorners(50))
            Glide.with(newsImage.context)
                .setDefaultRequestOptions(requestOptions)
                .load(item.urlToImage)
                .placeholder(R.drawable.progress_animation)
                .into(newsImage)
        }
    }

    inner class TrendingNewsViewHolder(itemView: View) : BaseViewHolder<TrendingNews>(itemView) {
        private val newsTitleTextView = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsImage = itemView.findViewById<AppCompatImageView>(R.id.newsImage)
        private val newsDescriptionTextView = itemView.findViewById<AppCompatTextView>(R.id.newsDescription)

        override fun bind(item: TrendingNews) {
            newsTitleTextView.text = if (item.title.isEmpty()) "Title Not Available " else item.title
            newsDescriptionTextView.text = item.description
            val requestOptions = RequestOptions().transform(RoundedCorners(50))
            Glide.with(newsImage.context)
                .setDefaultRequestOptions(requestOptions)
                .load(item.urlToImage)
                .placeholder(R.drawable.progress_animation)
                .into(newsImage)
        }
    }

    inner class PoliticalNewsViewHolder(itemView: View) : BaseViewHolder<PoliticalNews>(itemView) {
        private val newsTitleTextView = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsImage = itemView.findViewById<AppCompatImageView>(R.id.newsImage)

        override fun bind(item: PoliticalNews) {
            newsTitleTextView.text = item.title
            val requestOptions = RequestOptions().transform(RoundedCorners(50)).placeholder(R.drawable.banner_image)
            Glide.with(newsImage.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(item.urlToImage)
                .into(newsImage)
            //Do your view assignment here from the data model
        }
    }

    inner class MovieNewsNewsViewHolder(itemView: View) : BaseViewHolder<Movies>(itemView) {
        private val newsTitleTextView = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsImage = itemView.findViewById<AppCompatImageView>(R.id.newsImage)

        override fun bind(item: Movies) {
            newsTitleTextView.text = item.title
            val requestOptions = RequestOptions().transform(RoundedCorners(20)).placeholder(R.drawable.banner_image)
            Glide.with(newsImage.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(item.urlToImage)
                .into(newsImage)
        }
    }
}




