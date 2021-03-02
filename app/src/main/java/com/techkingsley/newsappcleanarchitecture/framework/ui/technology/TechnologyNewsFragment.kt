package com.techkingsley.newsappcleanarchitecture.framework.ui.technology

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.techkingsley.domain.entities.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.TechnologyNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.NewsAdapter
import com.techkingsley.presentation.technology.TechnologyNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        techNewsViewModel.cachedTechNews.observe(this.viewLifecycleOwner, Observer { techNews ->
            buildRecyclerView(techNews)
        })
    }

    private fun buildRecyclerView(news: List<News>) {
        with(viewBinding) {
            if (news.isNullOrEmpty().not()) {
                val adapter = NewsAdapter().apply { submitList(news) }
                technologyNewsRecyclerView.adapter = adapter
                technologyNewsRecyclerView.visibility = View.VISIBLE
                txErrorMessage.visibility = View.GONE
                loadingSpinner.visibility = View.GONE
            } else {
                technologyNewsRecyclerView.visibility = View.GONE
                txErrorMessage.visibility = View.VISIBLE
                loadingSpinner.visibility = View.GONE
            }
        }
    }
}