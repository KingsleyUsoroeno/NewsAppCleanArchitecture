package com.techkingsley.newsappcleanarchitecture.framework.ui.news.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techkingsley.domain.models.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.MovieFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.adapter.NewsAdapter
import com.techkingsley.presentation.hide
import com.techkingsley.presentation.movies.MovieViewModel
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieNewsFragment : Fragment(R.layout.movie_fragment) {

    private lateinit var viewBinding: MovieFragmentBinding
    private val movieViewModel: MovieViewModel by viewModels()

    companion object {
        fun newInstance() = MovieNewsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = MovieFragmentBinding.bind(requireView())
        viewBinding.movieViewModel = movieViewModel
        viewBinding.lifecycleOwner = this

        with(movieViewModel) {
            lifecycleScope.launchWhenStarted {
                viewState.collect {
                    when (it) {
                        is NewsUiState.Idle -> {

                        }
                        is NewsUiState.Loading -> {
                            viewBinding.loadingSpinner.show
                        }

                        is NewsUiState.Success -> {
                            viewBinding.loadingSpinner.hide
                            initMovieNewsAdapter(it.news)
                        }

                        is NewsUiState.Empty -> {
                            viewBinding.loadingSpinner.hide
                            viewBinding.textErrorMessage.text = getString(R.string.news_error_message)
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


    private fun initMovieNewsAdapter(items: List<News>) {
        with(viewBinding) {
            val adapter = NewsAdapter()
            adapter.submitList(items)
            movieNewsRecyclerView.adapter = adapter
        }
    }
}