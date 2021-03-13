package com.techkingsley.newsappcleanarchitecture.framework.ui.trending

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.techkingsley.domain.entities.news.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.TrendingNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingNewsFragment : Fragment(R.layout.trending_news_fragment) {

    companion object {
        fun newInstance() = TrendingNewsFragment()
    }

    private val viewModel: TrendingNewsViewModel by viewModels()
    private lateinit var viewBinding: TrendingNewsFragmentBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding = TrendingNewsFragmentBinding.bind(requireView())
        //viewBinding.trendingNewsViewModel = viewModel
        viewBinding.lifecycleOwner = this

//        viewModel.trendingNews.observe(this.viewLifecycleOwner, Observer {
//            buildRecyclerView(it)
//        })
//
//        viewModel.isNetworkErrorLiveData.observe(this.viewLifecycleOwner, Observer {
//
//        })
    }

    private fun buildRecyclerView(item: List<News>) {
        val adapter = NewsAdapter()
        adapter.submitList(item)
        viewBinding.technologyNewsRecyclerView.adapter = adapter
    }

}