package com.techkingsley.presentation.search

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.techkingsley.domain.models.params.SearchNewsParams
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.domain.usecases.news.ObserveNews
import com.techkingsley.domain.usecases.search.SearchNews
import com.techkingsley.domain.usecases.searchhistory.AddSearchHistory
import com.techkingsley.domain.usecases.searchhistory.DeleteSearchHistory
import com.techkingsley.domain.usecases.searchhistory.GetAllSearchHistory
import com.techkingsley.presentation.BaseViewModel
import com.techkingsley.presentation.from
import com.techkingsley.presentation.newsstate.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNews: SearchNews,
    getAllSearchHistory: GetAllSearchHistory,
    private val addSearchHistory: AddSearchHistory,
    private val observeNews: ObserveNews,
    private val deleteSearchHistory: DeleteSearchHistory
) : BaseViewModel<NewsUiState>(initialState = NewsUiState.Idle) {

    val allSearchHistory = getAllSearchHistory.execute().asLiveData()

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
    fun observeNews(query: String) = observeNews.execute(query).asLiveData()

    /*its a good practise to log the error and provide the user with a feedback on the UI*/
    fun fetchNews(query: String, hideKeyBoard: () -> Unit) {
        hideKeyBoard()
        viewModelScope.launch {
            searchNews.execute(SearchNewsParams(query, Date().from))
                .map { news ->
                    if (news.isNullOrEmpty().not()) {
                        setState(NewsUiState.Success(news))
                    } else {
                        setState(NewsUiState.Empty)
                    }
                }.onStart {
                    setState(NewsUiState.Loading)

                }.catch { error ->
                    println("Throwable from fetching movie news is $error")
                    setState(NewsUiState.Error(error.message ?: ""))

                }.collect {}
        }
    }
}