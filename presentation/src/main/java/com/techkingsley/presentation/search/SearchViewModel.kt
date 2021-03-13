package com.techkingsley.presentation.search


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.techkingsley.domain.entities.params.SearchNewsParams
import com.techkingsley.domain.entities.searchhistory.SearchHistory
import com.techkingsley.domain.usecases.news.ObserveNewsByCategory
import com.techkingsley.domain.usecases.search.SearchNews
import com.techkingsley.domain.usecases.searchhistory.AddSearchHistory
import com.techkingsley.domain.usecases.searchhistory.DeleteSearchHistory
import com.techkingsley.domain.usecases.searchhistory.GetAllSearchHistory
import com.techkingsley.presentation.from
import com.techkingsley.presentation.newsstate.NewsUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.*

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2

class SearchViewModel @ViewModelInject constructor(
    private val searchNews: SearchNews,
    getAllSearchHistory: GetAllSearchHistory,
    private val addSearchHistory: AddSearchHistory,
    private val observeNews: ObserveNewsByCategory,
    private val deleteSearchHistory: DeleteSearchHistory
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Idle)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<NewsUiState> = _uiState

    val allSearchHistory = getAllSearchHistory.invoke().asLiveData()

    fun addSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch {
            addSearchHistory.invoke(searchHistory)
        }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch {
            deleteSearchHistory.invoke(searchHistory)
        }
    }

    /** Use to Query News From the local database base on the news category passed in */
    fun observeNews(query: String) = observeNews.invoke(query).asLiveData()

    /*its a good practise to log the error and provide the user with a feedback on the UI*/
    fun fetchNews(query: String, hideKeyBoard: () -> Unit) {
        hideKeyBoard()
        viewModelScope.launch {
            searchNews.execute(SearchNewsParams(query, Date().from()))
                .onStart { _uiState.value = NewsUiState.Loading }
                .map { news ->
                    if (news.isNullOrEmpty().not()) {
                        _uiState.value = NewsUiState.Success(news)
                    } else {
                        _uiState.value = NewsUiState.Error("No news found with category, please try another keyword")
                    }
                }
                .catch { error ->
                    error.printStackTrace()
                    val message: String = if (error is UnknownHostException) "Please check your internet connection and try again"
                    else error.message ?: "something went wrong"
                    _uiState.value = NewsUiState.Error(message)
                    println("exception caught from searchVM is ${error.message}")
                }.collect {}
        }
    }
}