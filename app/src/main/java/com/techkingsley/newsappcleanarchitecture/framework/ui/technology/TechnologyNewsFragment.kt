package com.techkingsley.newsappcleanarchitecture.framework.ui.technology

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.techkingsley.domain.entities.News
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.TechnologyNewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TechnologyNewsFragment : Fragment(R.layout.technology_news_fragment) {

    private lateinit var viewBinding: TechnologyNewsFragmentBinding
    //private val techNewsViewModel: TechnologyNewsViewModel by viewModels()

    companion object {
        private const val TAG = "TechnologyNewsFragment"
        fun newInstance() = TechnologyNewsFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding = TechnologyNewsFragmentBinding.bind(requireView())
//        viewBinding.techViewModel = techNewsViewModel
//        viewBinding.lifecycleOwner = this
//        techNewsViewModel.techNews.observe(this.viewLifecycleOwner, Observer { techNews ->
//            if (techNews.isNullOrEmpty().not()) {
//                buildRecyclerView(techNews)
//            } else {
//                // TODO Show the user a Text on the Screen that the App Couldn't fetch the latest news
//                Log.i(TAG, "Couldn't fetch the latest news")
//            }
//        })
//
//        techNewsViewModel.isNetworkErrorLiveData.observe(this.viewLifecycleOwner, Observer {
//            //Toast.makeText(this.requireContext(), "Network Error", Toast.LENGTH_LONG).show()
//        })
    }

    private fun buildRecyclerView(item: List<News>) {
        val adapter = NewsAdapter()
        adapter.submitList(item)
        viewBinding.technologyNewsRecyclerView.adapter = adapter
    }
}