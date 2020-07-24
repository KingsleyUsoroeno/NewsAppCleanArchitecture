package com.techkingsley.newsappcleanarchitecture.framework.presentation.politics

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import com.techkingsley.newsappcleanarchitecture.databinding.PoliticalNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PoliticalNewsFragment : Fragment(R.layout.political_news_fragment) {

    private lateinit var viewBinding: PoliticalNewsFragmentBinding
    private val viewModel: PoliticalNewsViewModel by viewModels()

    companion object {
        fun newInstance() = PoliticalNewsFragment()
        private const val TAG = "PoliticalNewsFragment"
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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