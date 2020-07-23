package com.techkingsley.newsappcleanarchitecture.framework.presentation.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.NewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter.NewsTagRecyclerAdapter
import com.techkingsley.newsappcleanarchitecture.framework.presentation.movies.MovieFragment
import com.techkingsley.newsappcleanarchitecture.framework.presentation.politics.PoliticalNewsFragment
import com.techkingsley.newsappcleanarchitecture.framework.presentation.search.SearchActivity
import com.techkingsley.newsappcleanarchitecture.framework.presentation.technology.TechnologyNewsFragment
import com.techkingsley.newsappcleanarchitecture.framework.presentation.trending.TrendingNewsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.news_fragment), NewsTagRecyclerAdapter.OnItemClickedListener {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var viewBinding: NewsFragmentBinding
    private lateinit var fragmentContainer: ViewPager2

    companion object {
        private const val TAG = "NewsFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding = NewsFragmentBinding.bind(requireView())
        (activity as AppCompatActivity?)!!.setSupportActionBar(viewBinding.newsFragmentToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
        fragmentContainer = viewBinding.fragmentContainer
        buildTagRecyclerView()
        buildFragmentContainer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_fragment_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> this.requireActivity().startActivity(Intent(this.requireContext(), SearchActivity::class.java))
            R.id.action_notifications -> Log.i(TAG, "Notifications action was clicked")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buildTagRecyclerView() {
        val items = arrayListOf("Technology", "Trending", "Politics", "Movies")
        val tagAdapter = NewsTagRecyclerAdapter(items, this)
        viewBinding.newsTagRecyclerView.adapter = tagAdapter
    }

    private fun buildFragmentContainer() {
        val fragments = arrayListOf(TechnologyNewsFragment.newInstance(), TrendingNewsFragment.newInstance(), PoliticalNewsFragment.newInstance(), MovieFragment.newInstance())
        val fragmentAdapter = FragmentViewPagerAdapter(fragments, requireActivity())
        fragmentContainer.isUserInputEnabled = false
        fragmentContainer.adapter = fragmentAdapter
    }

    override fun onNewsTagClicked(tag: String) {
        when (tag) {
            "Technology" -> fragmentContainer.setCurrentItem(0, true)
            "Trending" -> fragmentContainer.setCurrentItem(1, true)
            "Politics" -> fragmentContainer.setCurrentItem(2, true)
            "Movies" -> fragmentContainer.setCurrentItem(3, true)
        }
    }
}