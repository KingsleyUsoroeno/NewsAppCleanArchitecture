package com.techkingsley.newsappcleanarchitecture.framework.ui.news.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.TrendingNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.adapter.NewsAdapter
import com.techkingsley.presentation.hide
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.show
import com.techkingsley.presentation.trending.TrendingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TrendingNewsFragment : Fragment(R.layout.trending_news_fragment) {

    companion object {
        fun newInstance() = TrendingNewsFragment()
    }

    private val viewModel: TrendingNewsViewModel by viewModels()
    private lateinit var viewBinding: TrendingNewsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = TrendingNewsFragmentBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                viewState.collect {
                    when (it) {
                        is NewsUiState.Idle -> {
                        }

                        is NewsUiState.Loading -> {
                            viewBinding.loadingSpinner.show
                        }
                        is NewsUiState.Empty -> {
                            viewBinding.loadingSpinner.hide
                            viewBinding.textErrorMessage.text = getString(R.string.news_error_message)
                        }

                        is NewsUiState.Success -> {
                            viewBinding.loadingSpinner.hide
                            buildRecyclerView(it.news)
                        }

                        is NewsUiState.Error -> {
                            viewBinding.loadingSpinner.hide
                            viewBinding.textErrorMessage.text = getString(R.string.news_error_message)
                        }
                    }
                }
            }
        }
    }

    private fun buildRecyclerView(item: List<News>) {
        val adapter = NewsAdapter()
        adapter.submitList(item)
        viewBinding.technologyNewsRecyclerView.adapter = adapter
    }

}