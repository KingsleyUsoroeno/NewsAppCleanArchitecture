package com.techkingsley.newsappcleanarchitecture.framework.presentation.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import com.techkingsley.newsappcleanarchitecture.databinding.MovieFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.movie_fragment) {

    private lateinit var viewBinding: MovieFragmentBinding
    private val movieViewModel: MovieViewModel by viewModels()

    companion object {
        fun newInstance() = MovieFragment()
        private const val TAG = "MovieFragment"
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding = MovieFragmentBinding.bind(requireView())
        viewBinding.movieViewModel = movieViewModel
        viewBinding.lifecycleOwner = this

        movieViewModel.movieNews.observe(this.viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty().not()) {
                buildRecyclerView(it)
            } else {
                Log.i(TAG, "couldn't fetch news from the server ask the user to try again")
            }
        })
    }

    private fun buildRecyclerView(items: List<News>) {
        val adapter = NewsAdapter()
        adapter.submitList(items)
        viewBinding.movieNewsRecyclerView.adapter = adapter
    }
}