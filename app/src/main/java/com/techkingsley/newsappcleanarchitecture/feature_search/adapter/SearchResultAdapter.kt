package com.techkingsley.newsappcleanarchitecture.feature_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.databinding.LayoutNewsSearchResultBinding

class SearchResultAdapter(
    private val searchResults: List<News>,
    private val onSearchResultClicked: (news: News) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = LayoutNewsSearchResultBinding.inflate(layoutInflater, parent, false)
        return SearchResultViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = searchResults.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.updateSearchResult(searchResults[position])
    }

    inner class SearchResultViewHolder(private val viewBinding: LayoutNewsSearchResultBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun updateSearchResult(bindingNews: News) {
            with(viewBinding) {
                news = bindingNews
                root.setOnClickListener { onSearchResultClicked(bindingNews) }
                executePendingBindings()
            }
        }
    }
}