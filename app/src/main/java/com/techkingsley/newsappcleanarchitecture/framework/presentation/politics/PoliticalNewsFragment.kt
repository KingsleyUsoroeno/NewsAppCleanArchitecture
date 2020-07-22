package com.techkingsley.newsappcleanarchitecture.framework.presentation.politics

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.PoliticalNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter.NewsAdapter

class PoliticalNewsFragment : Fragment(R.layout.political_news_fragment) {

    private lateinit var viewBinding: PoliticalNewsFragmentBinding

    companion object {
        fun newInstance() = PoliticalNewsFragment()
    }

    private lateinit var viewModel: PoliticalNewsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding = PoliticalNewsFragmentBinding.bind(requireView())
        viewModel = ViewModelProviders.of(this).get(PoliticalNewsViewModel::class.java)
        buildRecyclerView()
        // TODO: Use the ViewModel
    }

    fun buildRecyclerView() {
        val adapter = NewsAdapter()
        viewBinding.politicalNewsRecyclerView.adapter = adapter
    }

}