package com.techkingsley.newsappcleanarchitecture.feature_search

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.newsappcleanarchitecture.R
import com.techkingsley.newsappcleanarchitecture.databinding.ActivitySearchBinding
import com.techkingsley.newsappcleanarchitecture.feature_search.adapter.SearchHistoryAdapter
import com.techkingsley.newsappcleanarchitecture.feature_search.adapter.SearchResultAdapter


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySearchBinding

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var mSearchViewEdt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewBinding.lifecycleOwner = this

        lifecycleScope.launchWhenStarted {
            searchViewModel.searchResultState.collect {

                val loaderVisibility = if (it is SearchScreenResult.Loading) View.VISIBLE else View.GONE
                viewBinding.loadingSpinner.visibility = loaderVisibility

                val errorMessage = if (it is SearchScreenResult.Failure) it.errorMessage else ""
                viewBinding.textNewsException.apply {
                    visibility = if (errorMessage.isEmpty()) View.GONE else View.VISIBLE
                    text = errorMessage
                }


                if (it is SearchScreenResult.Success) {
                    viewBinding.recyclerSearchHistory.visibility = View.GONE
                    viewBinding.recyclerSearchResults.visibility = View.VISIBLE
                    val searchResultAdapter = SearchResultAdapter(it.searchResults) {}
                    viewBinding.recyclerSearchResults.adapter = searchResultAdapter
                    viewBinding.searchView.hideKeyboard(viewBinding.searchView)
                }
            }
        }

        with(viewBinding) {
            searchView.apply {
                setVoiceSearch(false)
                showSearch(false)
                setHint(getString(R.string.search_hint))

                val mBackButton = findViewById<ImageView>(R.id.action_up_btn)
                val mVoiceSearchButton = findViewById<ImageView>(R.id.action_voice_btn)
                val mEmptyButton = findViewById<ImageView>(R.id.action_empty_btn)
                mSearchViewEdt = findViewById(R.id.searchTextView)
                mVoiceSearchButton.visibility = View.GONE

                mEmptyButton.setOnClickListener { view ->
                    mVoiceSearchButton.visibility = View.VISIBLE
                    view.visibility = View.GONE
                    mSearchViewEdt.setText("")
                }

                mBackButton.setOnClickListener { onBackPressed() }

            }

            initSearchHistoryAdapter()

            searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Timber.i("query text submitted is $query")
                    searchViewModel.addSearchHistory(SearchHistory(searchTitle = query))
                    searchViewModel.fetchNews(query) { searchView.hideKeyboard(searchView) }
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Timber.i("query text submitted is $newText")
                    val visibilityToggle = if (newText.isNotEmpty()) View.GONE else View.VISIBLE
                    viewBinding.textNewsException.visibility = View.GONE
                    // viewBinding.recyclerSearchResults.visibility = View.GONE
                    viewBinding.recyclerSearchHistory.visibility = visibilityToggle

                    /** Get the results from the remote for now and showcase it to the user*/
                    //searchViewModel.queryChannel.offer(newText)
                    return true
                }
            })
        }
    }

    private fun initSearchHistoryAdapter() {
        lifecycleScope.launchWhenStarted {
            searchViewModel.allSearchHistory.collect { searchHistory ->
                val searchHistoryAdapter = SearchHistoryAdapter(
                    searchHistory = searchHistory,
                    onDeleteSearchHistory = searchViewModel::deleteSearchHistory,
                    onSearchHistoryClicked = {
                        mSearchViewEdt.setText(it.searchTitle)
                        searchViewModel.fetchNews(it.searchTitle) {
                            viewBinding.searchView.hideKeyboard(viewBinding.searchView)
                        }
                    }
                )
                viewBinding.recyclerSearchHistory.visibility = View.VISIBLE
                viewBinding.recyclerSearchHistory.adapter = searchHistoryAdapter
            }
        }
    }
}