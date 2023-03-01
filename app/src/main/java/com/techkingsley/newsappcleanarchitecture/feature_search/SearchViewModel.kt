package com.techkingsley.newsappcleanarchitecture.feature_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.domain.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _searchResultState = MutableStateFlow<SearchScreenResult>(SearchScreenResult.Idle)
    val searchResultState get() = _searchResultState.asStateFlow()

    val allSearchHistory: Flow<List<SearchHistory>> = newsRepository.observeSearchHistory()

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
        newsRepository.searchNews(query, "")
            .onStart {
                hideKeyBoard.invoke()
                _searchResultState.value = SearchScreenResult.Loading
            }
            .onEach { news ->
                _searchResultState.value = SearchScreenResult.Success(news)
            }
            .catch { throwable ->
                val errorMessage = throwable.message ?: "Nothing found for that search query, please search for something else"
                _searchResultState.value = SearchScreenResult.Failure(errorMessage)
            }
            .launchIn(viewModelScope)
    }
}

sealed class SearchScreenResult {
    object Idle : SearchScreenResult()
    object Loading : SearchScreenResult()
    data class Success(val searchResults: List<News>) : SearchScreenResult()
    data class Failure(val errorMessage: String) : SearchScreenResult()
}