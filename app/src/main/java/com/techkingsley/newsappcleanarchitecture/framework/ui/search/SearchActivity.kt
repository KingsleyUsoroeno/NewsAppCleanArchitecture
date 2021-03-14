package com.techkingsley.newsappcleanarchitecture.framework.ui.search

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.ActivitySearchBinding
import com.techkingsley.newsappcleanarchitecture.framework.adapter.SearchHistoryAdapter
import com.techkingsley.newsappcleanarchitecture.framework.adapter.SearchResultAdapter
import com.techkingsley.presentation.hide
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.search.SearchViewModel
import com.techkingsley.presentation.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchResultAdapter.OnItemClickedListener,
    SearchHistoryAdapter.OnItemClickedListener {

    private lateinit var viewBinding: ActivitySearchBinding

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var mSearchViewEdt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        //activityViewBinding.searchActivityViewModel = searchViewModel
        viewBinding.lifecycleOwner = this


        lifecycleScope.launchWhenStarted {
            searchViewModel.viewState.collect {
                when (it) {
                    is NewsUiState.Idle -> {
                    }
                    is NewsUiState.Loading -> {
                        viewBinding.loadingSpinner.show
                    }
                    is NewsUiState.Success -> {
                        viewBinding.loadingSpinner.hide
                        viewBinding.recyclerSearchResults.show
                        viewBinding.recyclerSearchHistory.hide
                        searchResultAdapter = SearchResultAdapter(this@SearchActivity)
                        searchResultAdapter.setSearchResults(it.news)
                        viewBinding.recyclerSearchResults.adapter = searchHistoryAdapter
                        viewBinding.searchView.hideKeyboard(viewBinding.searchView)
                    }

                    is NewsUiState.Error -> {
                        viewBinding.textNewsException.show
                        viewBinding.loadingSpinner.show
                        viewBinding.textNewsException.text = it.exception
                    }

                    is NewsUiState.Empty -> {

                    }
                }
            }
        }

        with(viewBinding) {
            searchView.setVoiceSearch(false)
            searchView.showSearch(false)
            searchView.setHint(getString(R.string.search_hint))

            val mBackButton = searchView.findViewById<ImageView>(R.id.action_up_btn)
            val mVoiceSearchButton = searchView.findViewById<ImageView>(R.id.action_voice_btn)
            val mEmptyButton = searchView.findViewById<ImageView>(R.id.action_empty_btn)
            mSearchViewEdt = searchView.findViewById(R.id.searchTextView)
            mVoiceSearchButton.hide

            mEmptyButton.setOnClickListener { view ->
                mVoiceSearchButton.show
                view.hide
                mSearchViewEdt.setText("")
            }

            mBackButton.setOnClickListener { onBackPressed() }

            searchViewModel.allSearchHistory.observe(this@SearchActivity, Observer {
                it?.let {
                    textNewsException.hide
                    searchHistoryAdapter = SearchHistoryAdapter(this@SearchActivity)
                    recyclerSearchHistory.adapter = searchHistoryAdapter
                    searchHistoryAdapter.setSearchResults(it)
                }
            })

            searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Timber.i("query text submitted is $query")
                    val searchHistory = SearchHistory(searchTitle = query)
                    searchViewModel.addSearchHistory(searchHistory)
                    searchViewModel.fetchNews(query) { searchView.hideKeyboard(searchView) }
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Timber.i("query text submitted is $newText")
                    if (newText.isNotEmpty()) {
                        viewBinding.textNewsException.hide
                        viewBinding.recyclerSearchHistory.hide
                        viewBinding.recyclerSearchResults.hide
                        /** Get the results from the remote for now and showcase it to the user*/
                        //searchViewModel.queryChannel.offer(newText)
                    } else {
                        //mVoiceSearchButton.visibility = View.VISIBLE
                        viewBinding.textNewsException.hide
                        viewBinding.recyclerSearchHistory.show
                        viewBinding.recyclerSearchResults.hide
                    }
                    return true
                }
            })
        }
    }

    override fun onSearchResultClicked(news: News) {
        Timber.i("SearchResult Clicked Was $news")
    }

    override fun onSearchHistoryClicked(searchHistory: SearchHistory) {
        // Comment if you don't want the searchView to show the user the previous search result as the current search keyword on the searchView
        mSearchViewEdt.setText(searchHistory.searchTitle)
        searchViewModel.fetchNews(searchHistory.searchTitle) {
            viewBinding.searchView.hideKeyboard(viewBinding.searchView)
        }
    }

    override fun onDeleteSearchHistoryClicked(searchHistory: SearchHistory) {
        searchViewModel.deleteSearchHistory(searchHistory)
    }
}