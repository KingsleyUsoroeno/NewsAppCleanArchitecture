package com.techkingsley.newsappcleanarchitecture.framework.ui.technology

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techkingsley.domain.entities.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.TechnologyNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.NewsAdapter
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.technology.TechnologyNewsViewModel
import com.techkingsley.presentation.utils.hide
import com.techkingsley.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class TechnologyNewsFragment : Fragment(R.layout.technology_news_fragment) {

    private lateinit var viewBinding: TechnologyNewsFragmentBinding
    private val techNewsViewModel: TechnologyNewsViewModel by viewModels()

    companion object {
        fun newInstance() = TechnologyNewsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = TechnologyNewsFragmentBinding.bind(requireView())
        viewBinding.techViewModel = techNewsViewModel
        viewBinding.lifecycleOwner = this

        lifecycleScope.launchWhenStarted {
            // Triggers the flow and starts listening for values
            techNewsViewModel.uiState.collect {
                when (it) {
                    is NewsUiState.Idle -> {
                        Timber.i("Am in an idle state")
                    }

                    is NewsUiState.Loading -> {
                        viewBinding.loadingSpinner.show()
                    }

                    is NewsUiState.Success -> {
                        viewBinding.loadingSpinner.hide()
                        buildRecyclerView(it.news)
                    }

                    is NewsUiState.Error -> {
                        viewBinding.loadingSpinner.hide()
                        viewBinding.txErrorMessage.text = it.exception
                    }
                }
            }
        }
    }

    private fun buildRecyclerView(news: List<News>) {
        with(viewBinding) {
            val adapter = NewsAdapter().apply { submitList(news) }
            technologyNewsRecyclerView.adapter = adapter
        }
    }
}