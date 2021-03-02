package com.techkingsley.newsappcleanarchitecture.framework.ui.search

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.techkingsley.domain.entities.News
import com.techkingsley.domain.entities.SearchHistory
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.ActivitySearchBinding
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.SearchHistoryAdapter
import com.techkingsley.newsappcleanarchitecture.framework.ui.adapter.SearchResultAdapter
import com.techkingsley.presentation.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchResultAdapter.OnItemClickedListener,
    SearchHistoryAdapter.OnItemClickedListener {

    private lateinit var activityViewBinding: ActivitySearchBinding

    private val searchViewModel: SearchViewModel by viewModels()
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
        //activityViewBinding.searchActivityViewModel = searchViewModel
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
                activityViewBinding.textViewNewsException.visibility = View.GONE
                searchHistoryAdapter = SearchHistoryAdapter(this)
                activityViewBinding.recyclerSearchHistory.adapter = searchHistoryAdapter
                searchHistoryAdapter.setSearchResults(it)
            }
        })

        searchViewModel.loadingState.observe(this, Observer {
            val visibility = if (it) View.VISIBLE else View.GONE
            activityViewBinding.loadingSpinner.visibility = visibility
            if (it) {
                activityViewBinding.recyclerSearchResults.visibility = View.GONE
            }
        })

        searchViewModel.searchResult.observe(this, Observer { news ->
            if (news.isNullOrEmpty()) {
                // Failed to fetch the news from the server due to a server failure or low internet connection
                Timber.i("Failed to fetch users news")
                activityViewBinding.textViewNewsException.visibility = View.VISIBLE
                activityViewBinding.recyclerSearchResults.visibility = View.GONE

            } else {
                Timber.i("News Network Entity is $news")
                activityViewBinding.textViewNewsException.visibility = View.GONE
                activityViewBinding.recyclerSearchResults.visibility = View.VISIBLE
                searchResultAdapter = SearchResultAdapter(this)
                searchResultAdapter.setSearchResults(news)
                activityViewBinding.recyclerSearchResults.adapter = searchResultAdapter
                searchView.hideKeyboard(searchView)
            }
        })

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Timber.i("query text submitted is $query")
                val searchHistory = SearchHistory(searchTitle = query)
                searchViewModel.addSearchHistory(searchHistory)
                searchViewModel.fetchNews(query) { searchView.hideKeyboard(searchView) }
                //searchViewModel.queryChannel.offer(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Timber.i("query text submitted is $newText")
                if (newText.isNotEmpty()) {
                    activityViewBinding.textViewNewsException.visibility = View.GONE
                    activityViewBinding.recyclerSearchHistory.visibility = View.GONE
                    activityViewBinding.recyclerSearchResults.visibility = View.VISIBLE
                    /** Get the results from the remote for now and showcase it to the user*/
                    //searchViewModel.queryChannel.offer(newText)
                } else {
                    mVoiceSearchButton.visibility = View.VISIBLE
                    activityViewBinding.textViewNewsException.visibility = View.GONE
                    activityViewBinding.recyclerSearchHistory.visibility = View.VISIBLE
                    activityViewBinding.recyclerSearchResults.visibility = View.GONE
                }
                return true
            }
        })
    }

    override fun onSearchResultClicked(news: News) {
        Timber.i("SearchResult Clicked Was $news")
    }

    override fun onSearchHistoryClicked(searchHistory: SearchHistory) {
        // Comment if you don't want the searchView to show the user the previous search result as the current search keyword on the searchView
        mSearchViewEdt.setText(searchHistory.searchTitle)
        searchViewModel.fetchNews(searchHistory.searchTitle) { searchView.hideKeyboard(searchView) }
    }

    override fun onDeleteSearchHistoryClicked(searchHistory: SearchHistory) {
        searchViewModel.deleteSearchHistory(searchHistory)
    }
}