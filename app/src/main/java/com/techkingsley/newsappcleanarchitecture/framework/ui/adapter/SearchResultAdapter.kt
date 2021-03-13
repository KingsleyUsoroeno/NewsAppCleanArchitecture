package com.techkingsley.newsappcleanarchitecture.framework.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techkingsley.domain.entities.news.News
import com.techkingsley.newsappcleanarchitecture.databinding.LayoutNewsSearchResultBinding

class SearchResultAdapter(private val onNewsResultClicked: OnItemClickedListener) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private var searchResults = emptyList<News>()

    interface OnItemClickedListener {
        fun onSearchResultClicked(news: News)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = LayoutNewsSearchResultBinding.inflate(layoutInflater, parent, false)
        return SearchResultViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    fun setSearchResults(searchResults: List<News>) {
        this.searchResults = searchResults
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.updateSearchResult(searchResults[position])
    }

    inner class SearchResultViewHolder(private val viewBinding: LayoutNewsSearchResultBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun updateSearchResult(bindingNews: News) {
            with(viewBinding) {
                news = bindingNews
                resultCallback = onNewsResultClicked
                executePendingBindings()
            }
        }
    }
}