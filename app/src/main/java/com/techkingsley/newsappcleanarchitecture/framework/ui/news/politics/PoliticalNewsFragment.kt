package com.techkingsley.newsappcleanarchitecture.framework.ui.news.politics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.PoliticalNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.adapter.NewsAdapter
import com.techkingsley.presentation.hide
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.politics.PoliticalNewsViewModel
import com.techkingsley.presentation.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class PoliticalNewsFragment : Fragment(R.layout.political_news_fragment) {

    private lateinit var viewBinding: PoliticalNewsFragmentBinding
    private val viewModel: PoliticalNewsViewModel by viewModels()

    companion object {
        fun newInstance() = PoliticalNewsFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = PoliticalNewsFragmentBinding.bind(requireView())
        viewBinding.politicalNewsViewModel = viewModel
        viewBinding.lifecycleOwner = this

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                viewState.collect {
                    when (it) {
                        is NewsUiState.Idle -> {
                            Timber.i("Am in an idle state")
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
        viewBinding.politicalNewsRecyclerView.adapter = adapter
    }
}