package com.techkingsley.newsappcleanarchitecture.framework.presentation.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.MovieFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter.NewsAdapter

class MovieFragment : Fragment(R.layout.movie_fragment) {

    private lateinit var viewBinding: MovieFragmentBinding

    companion object {
        fun newInstance() = MovieFragment()
    }

    private lateinit var viewModel: MovieViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding = MovieFragmentBinding.bind(requireView())
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        // TODO: Use the ViewModel

        buildRecyclerView()
    }

    private fun buildRecyclerView() {
        val adapter = NewsAdapter()
        viewBinding.movieNewsRecyclerView.adapter = adapter
    }
}