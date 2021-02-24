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

    companion object {
        private const val TYPE_TECHNOLOGY = 0
        private const val TYPE_TRENDING = 1
        private const val TYPE_POLITICS = 2
        private const val TYPE_MOVIES = 3
    }

    fun submitList(item: List<Any>) {
        adapterDataList = item
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_news, parent, false)

        return when (viewType) {
            TYPE_TECHNOLOGY -> TechnologyNewsViewHolder(view)

            TYPE_TRENDING -> TrendingNewsViewHolder(view)

            TYPE_POLITICS -> PoliticalNewsViewHolder(view)

            TYPE_MOVIES -> MovieNewsNewsViewHolder(view)

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }*/

    /*override fun getItemViewType(position: Int): Int {
        return when (adapterDataList[position]) {
            is TechnologyNews -> TYPE_TECHNOLOGY
            is TrendingNews -> TYPE_TRENDING
            is PoliticalNews -> TYPE_POLITICS
            is Movies -> TYPE_MOVIES
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }*/

    override fun getItemCount(): Int {
        return adapterDataList.size
    }

    /*override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        when (holder) {
            is TechnologyNewsViewHolder -> holder.bind(element as TechnologyNews)
            is TrendingNewsViewHolder -> holder.bind(element as TrendingNews)
            is PoliticalNewsViewHolder -> holder.bind(element as PoliticalNews)
            is MovieNewsNewsViewHolder -> holder.bind(element as Movies)
            else -> throw IllegalArgumentException()
        }
    }*/

    /** Create a Class Called ImageLoader that will be responsible for loading a single news image onto the Screen*/
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newsTitleTextView = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsImage = itemView.findViewById<AppCompatImageView>(R.id.newsImage)
        private val newsDescriptionTextView = itemView.findViewById<AppCompatTextView>(R.id.newsDescription)

        fun bind(item: News) {
            newsTitleTextView.text = item.title
            newsDescriptionTextView.text = item.description
            val requestOptions = RequestOptions().transform(RoundedCorners(50))
            Glide.with(newsImage.context)
                .setDefaultRequestOptions(requestOptions)
                .load(item.urlToImage)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_broken_image)
                .into(newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(adapterDataList[position] as News)
    }

    /* inner class TrendingNewsViewHolder(itemView: View) : BaseViewHolder<TrendingNews>(itemView) {
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
                 .error(R.drawable.banner_image)
                 .placeholder(R.drawable.progress_animation)
                 .into(newsImage)
         }
     }

     inner class PoliticalNewsViewHolder(itemView: View) : BaseViewHolder<PoliticalNews>(itemView) {
         private val newsTitleTextView = itemView.findViewById<TextView>(R.id.newsTitle)
         private val newsImage = itemView.findViewById<AppCompatImageView>(R.id.newsImage)

         override fun bind(item: PoliticalNews) {
             //Do your view assignment here from the data model
             newsTitleTextView.text = item.title
             val requestOptions = RequestOptions().transform(RoundedCorners(50)).placeholder(R.drawable.banner_image)
             Glide.with(newsImage.context)
                 .applyDefaultRequestOptions(requestOptions)
                 .load(item.urlToImage)
                 .error(R.drawable.banner_image)
                 .into(newsImage)
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
     }*/
}




