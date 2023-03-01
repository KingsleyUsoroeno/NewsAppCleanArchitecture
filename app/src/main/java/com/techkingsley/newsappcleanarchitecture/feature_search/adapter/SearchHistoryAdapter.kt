package com.techkingsley.newsappcleanarchitecture.feature_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.LayoutSearchHistoryBinding


class SearchHistoryAdapter(
    private val onSearchHistoryClicked: (SearchHistory) -> Unit,
    private val onDeleteSearchHistory: (SearchHistory) -> Unit,
    private val searchHistory: List<SearchHistory>
) : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_search_history, parent, false)
        val viewBinding = LayoutSearchHistoryBinding.bind(view)
        return SearchHistoryViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = searchHistory.size

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.updateSearchHistory(searchHistory[position])
    }

    inner class SearchHistoryViewHolder(private val viewBinding: LayoutSearchHistoryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun updateSearchHistory(history: SearchHistory) {
            with(viewBinding) {
                this.searchHistory = history
                root.setOnClickListener { onSearchHistoryClicked(history) }
                imgDelete.setOnClickListener { onDeleteSearchHistory(history) }
                executePendingBindings()
            }
        }
    }
}
