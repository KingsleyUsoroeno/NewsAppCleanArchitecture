package com.techkingsley.newsappcleanarchitecture.framework.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.techkingsley.domain.entities.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.MovieFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.movie_fragment) {

    private lateinit var viewBinding: MovieFragmentBinding
    private val movieViewModel: MovieViewModel by viewModels()

    companion object {
        fun newInstance() = MovieFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = MovieFragmentBinding.bind(requireView())
        viewBinding.movieViewModel = movieViewModel
        viewBinding.lifecycleOwner = this

        movieViewModel.news.observe(viewLifecycleOwner, Observer { news ->
            if (news.isNullOrEmpty().not()) {
                buildRecyclerView(news)
            }
        })

        movieViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            Timber.i("error fetching news is $it")
        })
    }

    private fun buildRecyclerView(items: List<News>) {
        with(viewBinding) {
            if (items.isEmpty()) {
                textErrorMessage.visibility = View.VISIBLE
                movieNewsRecyclerView.visibility = View.GONE
            } else {
                textErrorMessage.visibility = View.GONE
                movieNewsRecyclerView.visibility = View.VISIBLE
                val adapter = NewsAdapter()
                adapter.submitList(items)
                viewBinding.movieNewsRecyclerView.adapter = adapter
            }
        }
    }
}