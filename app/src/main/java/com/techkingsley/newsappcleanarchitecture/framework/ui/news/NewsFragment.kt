package com.techkingsley.newsappcleanarchitecture.framework.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.NewsFragmentBinding
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.NewsTagRecyclerAdapter
import com.techkingsley.newsappcleanarchitecture.framework.ui.movies.MovieFragment
import com.techkingsley.newsappcleanarchitecture.framework.ui.politics.PoliticalNewsFragment
import com.techkingsley.newsappcleanarchitecture.framework.ui.search.SearchActivity
import com.techkingsley.newsappcleanarchitecture.framework.ui.technology.TechnologyNewsFragment
import com.techkingsley.newsappcleanarchitecture.framework.ui.trending.TrendingNewsFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.news_fragment), NewsTagRecyclerAdapter.OnItemClickedListener {

    private lateinit var viewBinding: NewsFragmentBinding
    private lateinit var fragmentContainer: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            R.id.action_notifications -> Timber.i("Notifications action was clicked")
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