package com.techkingsley.newsappcleanarchitecture.framework.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.databinding.ActivitySearchBinding
import com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter.SearchHistoryAdapter
import com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@AndroidEntryPoint
@FlowPreview
@ExperimentalCoroutinesApi
class SearchActivity : AppCompatActivity(), SearchHistoryAdapter.OnItemClickedListener, SearchResultAdapter.OnItemClickedListener {

    private lateinit var activityViewBinding: ActivitySearchBinding
    private val searchViewModel: SearchActivityViewModel by viewModels()
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var searchView: MaterialSearchView
    private lateinit var mSearchViewEdt: EditText

    companion object {
        private const val TAG = "SearchActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        activityViewBinding.searchActivityViewModel = searchViewModel
        activityViewBinding.lifecycleOwner = this

        searchView = activityViewBinding.searchView
        searchView.setVoiceSearch(false)
        searchView.showSearch(false)
        searchView.setHint(getString(R.string.search_hint))

        val mBackButton = searchView.findViewById<ImageView>(R.id.action_up_btn)
        val mVoiceSearchButton = searchView.findViewById<ImageView>(R.id.action_voice_btn)
        val mEmptyButton = searchView.findViewById<ImageView>(R.id.action_empty_btn)
        mSearchViewEdt = searchView.findViewById(R.id.searchTextView)
        mVoiceSearchButton.visibility = View.GONE

        mEmptyButton.setOnClickListener { view ->
            mVoiceSearchButton.visibility = View.VISIBLE
            view.visibility = View.GONE
            mSearchViewEdt.setText("")
        }

        mBackButton.setOnClickListener {
            onBackPressed()
        }

        searchViewModel.allSearchHistory.observe(this, Observer {
            it?.let {
                searchHistoryAdapter = SearchHistoryAdapter(this)
                activityViewBinding.recyclerSearchHistory.adapter = searchHistoryAdapter
                searchHistoryAdapter.setSearchResults(it)
            }
        })

        searchViewModel.searchResult.observe(this, Observer { news ->
            if (news.isNullOrEmpty().not()) {
                Log.i(TAG, "News Network Entity is $news")
                activityViewBinding.textViewNewsException.visibility = View.GONE
                activityViewBinding.recyclerSearchResults.visibility = View.VISIBLE
                searchResultAdapter = SearchResultAdapter(this)
                searchResultAdapter.setSearchResults(news)
                activityViewBinding.recyclerSearchResults.adapter = searchResultAdapter
                //activityViewBinding.recyclerSearchResults.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                searchView.hideKeyboard(searchView)
            } else {
                // Failed to fetch the news from the server due to a server failure or low internet connection
                Log.i(TAG, "Failed to fetch users news")
                activityViewBinding.textViewNewsException.visibility = View.VISIBLE
                activityViewBinding.recyclerSearchResults.visibility = View.GONE
            }
        })

        searchViewModel.errorState.observe(this, Observer {

        })

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i(TAG, "query text submitted is $query")
                val searchHistory = SearchHistory(searchTitle = query)
                searchViewModel.addSearchHistory(searchHistory)
                searchViewModel.fetchNews(query) { searchView.hideKeyboard(searchView) }
                //searchViewModel.queryChannel.offer(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.i(TAG, "query text changed is $newText")
                if (newText.isNotEmpty()) {
                    activityViewBinding.recyclerSearchHistory.visibility = View.GONE
                    activityViewBinding.recyclerSearchResults.visibility = View.VISIBLE
                    /** Get the results from the remote for now and showcase it to the user*/
                    //searchViewModel.queryChannel.offer(newText)
                } else {
                    mVoiceSearchButton.visibility = View.VISIBLE
                    activityViewBinding.recyclerSearchHistory.visibility = View.VISIBLE
                    activityViewBinding.recyclerSearchResults.visibility = View.GONE
                }
                return true
            }
        })
    }

    override fun onSearchHistoryClicked(searchHistory: SearchHistory) {
        Log.i(TAG, "I clicked on my previous history $searchHistory")
        // Comment if you don't want the searchView to show the user the previous search result as the current search keyword on the searchView
        mSearchViewEdt.setText(searchHistory.searchTitle)
        searchViewModel.fetchNews(searchHistory.searchTitle) { searchView.hideKeyboard(searchView) }
    }

    override fun onDeleteSearchHistoryClicked(searchHistory: SearchHistory) {
        Log.i(TAG, "onDeleteSearchHistoryClicked $searchHistory")
        searchViewModel.deleteSearchHistory(searchHistory)
    }

    override fun onSearchResultClicked(newsNetworkEntity: NewsNetworkEntity) {
        Log.i(TAG, "SearchResult Clicked Was $newsNetworkEntity")
    }
}