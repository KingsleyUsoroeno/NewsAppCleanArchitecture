package com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory


class SearchHistoryAdapter(private val onItemClickedListener: OnItemClickedListener) : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    var searchHistoryList = emptyList<SearchHistory>()

    interface OnItemClickedListener {
        fun onSearchHistoryClicked(searchHistory: SearchHistory)
        fun onDeleteSearchHistoryClicked(searchHistory: SearchHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.row_search_history, parent, false)
        return SearchHistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    fun setSearchResults(searchHistoryList: List<SearchHistory>) {
        this.searchHistoryList = searchHistoryList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.updateSearchHistory(searchHistoryList[position])
    }

    inner class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView = itemView.findViewById<AppCompatTextView>(R.id.textViewSearchQuery)
        private val imageView = itemView.findViewById<AppCompatImageView>(R.id.imgDelete)

        private val view = itemView.rootView

        fun updateSearchHistory(searchHistory: SearchHistory) {
            textView.text = searchHistory.searchTitle
            view.setOnClickListener { onItemClickedListener.onSearchHistoryClicked(searchHistory) }
            imageView.setOnClickListener { onItemClickedListener.onDeleteSearchHistoryClicked(searchHistory) }
        }
    }
}
