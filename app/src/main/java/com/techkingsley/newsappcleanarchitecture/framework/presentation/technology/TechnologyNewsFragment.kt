package com.techkingsley.newsappcleanarchitecture.framework.presentation.technology

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import com.techkingsley.newsappcleanarchitecture.databinding.TechnologyNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TechnologyNewsFragment : Fragment(R.layout.technology_news_fragment) {

    private lateinit var viewBinding: TechnologyNewsFragmentBinding
    private val techNewsViewModel: TechnologyNewsViewModel by viewModels()

    companion object {
        fun newInstance() = TechnologyNewsFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding = TechnologyNewsFragmentBinding.bind(requireView())
        viewBinding.techViewModel = techNewsViewModel
        viewBinding.lifecycleOwner = this
        techNewsViewModel.techNews.observe(this.viewLifecycleOwner, Observer {
            buildRecyclerView(it)
        })

        techNewsViewModel.isNetworkErrorLiveData.observe(this.viewLifecycleOwner, Observer {

        })
    }


    private fun buildRecyclerView(item: List<TechnologyNews>) {
        val adapter = NewsAdapter()
        adapter.setItem(item)
        viewBinding.technologyNewsRecyclerView.adapter = adapter
    }
}