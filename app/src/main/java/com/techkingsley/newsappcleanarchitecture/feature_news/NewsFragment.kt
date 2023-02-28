package com.techkingsley.newsappcleanarchitecture.feature_news

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.NewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.feature_news.adapter.NewsAdapter
import com.techkingsley.newsappcleanarchitecture.feature_search.SearchActivity
import com.techkingsley.newsappcleanarchitecture.utils.NewsConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.news_fragment) {

    private lateinit var viewBinding: NewsFragmentBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = NewsFragmentBinding.bind(requireView())
        (activity as AppCompatActivity?)!!.setSupportActionBar(viewBinding.newsFragmentToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {

                viewBinding.newsLayout.loadingSpinner.visibility = if (it.isLoading)
                    View.VISIBLE else View.GONE

                viewBinding.newsLayout.newsRecyclerView.visibility = if (it.isLoading.not() && it.news.isNotEmpty())
                    View.VISIBLE else View.GONE

                viewBinding.newsLayout.textErrorMessage.visibility = if (it.throwable != null)
                    View.VISIBLE else View.GONE

                initNewsRecyclerView(it.news)

                viewBinding.newsLayout.textErrorMessage.text = getString(R.string.news_error_message)
            }
        }

        with(viewBinding) {
            trendingNewsChip.setOnClickListener {
                viewModel.onNewsCategorySelected(NewsConstants.TRENDING_NEWS)
            }
            politicsNewsChip.setOnClickListener {
                viewModel.onNewsCategorySelected(NewsConstants.POLITICAL_NEWS)
            }
            technologyNewsChip.setOnClickListener {
                viewModel.onNewsCategorySelected(NewsConstants.TECH_NEWS)
            }
            moviesChip.setOnClickListener { viewModel.onNewsCategorySelected(NewsConstants.MOVIE_NEWS) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_fragment_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> this.requireActivity()
                .startActivity(Intent(this.requireContext(), SearchActivity::class.java))
            R.id.action_notifications -> Timber.i("Notifications action was clicked")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initNewsRecyclerView(news: List<News>) {
        with(viewBinding) {
            val adapter = NewsAdapter(viewModel::toggleNewsBookmarkStatus).apply {
                submitList(news)
            }
            newsLayout.newsRecyclerView.adapter = adapter
            newsLayout.newsRecyclerView.setHasFixedSize(true)
        }
    }
}