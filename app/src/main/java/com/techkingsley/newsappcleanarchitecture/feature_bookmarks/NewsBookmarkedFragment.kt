package com.techkingsley.newsappcleanarchitecture.feature_bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.FragmentNewsBookmarkedBinding
import com.techkingsley.newsappcleanarchitecture.feature_bookmarks.adapter.BookmarkedNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NewsBookmarkedFragment : Fragment(R.layout.fragment_news_bookmarked) {

    private lateinit var viewBinding: FragmentNewsBookmarkedBinding
    private val viewModel: BookmarkedNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentNewsBookmarkedBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.bookmarkedNews.collect { bookmarkedNews ->
                buildRecyclerView(bookmarkedNews.asReversed())
            }
        }
    }


    private fun buildRecyclerView(items: List<News> = emptyList()) {
        val adapter = BookmarkedNewsAdapter().apply { submitList(items) }
        viewBinding.newsBookmarkedRecyclerView.adapter = adapter
        viewBinding.newsBookmarkedRecyclerView.visibility = if (items.isNotEmpty()) View.VISIBLE else View.GONE
        viewBinding.emptyBookmarkTextView.visibility = if(items.isEmpty()) View.VISIBLE else View.GONE
    }
}