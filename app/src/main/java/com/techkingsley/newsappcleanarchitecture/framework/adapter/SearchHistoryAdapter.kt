package com.techkingsley.newsappcleanarchitecture.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.LayoutSearchHistoryBinding


class SearchHistoryAdapter(private val onItemClickedListener: OnItemClickedListener) :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    var searchHistoryList = emptyList<SearchHistory>()

    interface OnItemClickedListener {
        fun onSearchHistoryClicked(searchHistory: SearchHistory)
        fun onDeleteSearchHistoryClicked(searchHistory: SearchHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_search_history, parent, false)
        val viewBinding = LayoutSearchHistoryBinding.bind(view)
        return SearchHistoryViewHolder(viewBinding)
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

    inner class SearchHistoryViewHolder(private val viewBinding: LayoutSearchHistoryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun updateSearchHistory(history: SearchHistory) {
            with(viewBinding) {
                this.searchHistory = history
                this.onItemClickListener = onItemClickedListener
                root.setOnClickListener { onItemClickedListener.onSearchHistoryClicked(history) }
                executePendingBindings()
            }
        }
    }
}
