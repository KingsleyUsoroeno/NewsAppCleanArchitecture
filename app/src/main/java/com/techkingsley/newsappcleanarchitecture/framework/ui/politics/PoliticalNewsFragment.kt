package com.techkingsley.newsappcleanarchitecture.framework.ui.politics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.techkingsley.domain.entities.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.PoliticalNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.NewsAdapter
import com.techkingsley.presentation.politics.PoliticalNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        viewModel.politicalNews.observe(this.viewLifecycleOwner, Observer {
            buildRecyclerView(it)
        })

        viewModel.isNetworkErrorLiveData.observe(this.viewLifecycleOwner, Observer {

        })
    }

    private fun buildRecyclerView(item: List<News>) {
        val adapter = NewsAdapter()
        adapter.submitList(item)
        viewBinding.politicalNewsRecyclerView.adapter = adapter
    }
}