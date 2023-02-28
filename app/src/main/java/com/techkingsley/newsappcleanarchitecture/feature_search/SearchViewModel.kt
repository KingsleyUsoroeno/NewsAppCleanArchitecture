package com.techkingsley.newsappcleanarchitecture.feature_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.domain.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val allSearchHistory = newsRepository.observeSearchHistory().asLiveData()

    fun addSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch { newsRepository.insertSearchHistory(searchHistory) }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch { newsRepository.deleteSearchHistory(searchHistory) }
    }

    /** Use to Query News From the local database base on the news category passed in */
    //fun observeNews(query: String) = observeNews.execute(query).asLiveData()

    /*its a good practise to log the error and provide the user with a feedback on the UI*/
    fun fetchNews(query: String, hideKeyBoard: () -> Unit) {
        hideKeyBoard()
//        viewModelScope.launch {
//            searchNews.execute(SearchNewsParams(query, Date().from))
//                .map { news ->
//                    if (news.isNullOrEmpty().not()) {
//                        setState(NewsUiState.Success(news))
//                    } else {
//                        setState(NewsUiState.Empty)
//                    }
//                }.onStart {
//                    setState(NewsUiState.Loading)
//
//                }.catch { error ->
//                    println("Throwable from fetching movie news is $error")
//                    setState(NewsUiState.Error(error.message ?: ""))
//
//                }.collect {}
//        }
    }
}