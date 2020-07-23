package com.techkingsley.newsappcleanarchitecture.framework.presentation.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory
import com.techkingsley.newsappcleanarchitecture.business.data.cache.repository.NewsAppRepository
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.interactors.doIfFailure
import com.techkingsley.newsappcleanarchitecture.business.interactors.doIfNetworkException
import com.techkingsley.newsappcleanarchitecture.business.interactors.doIfSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2

@ExperimentalCoroutinesApi
@FlowPreview
class SearchActivityViewModel @ViewModelInject constructor(private val newsAppRepository: NewsAppRepository) : ViewModel() {

    val allSearchHistory = newsAppRepository.getSearchHistory().asLiveData()

    fun addSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        newsAppRepository.addSearchHistory(searchHistory)
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        newsAppRepository.deleteSearchHistory(searchHistory)
    }

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    private val _loadingStateLiveData = MutableLiveData<Boolean>(false)

    val loadingState: LiveData<Boolean>
        get() = _loadingStateLiveData

    val news = queryChannel
        //1
        .asFlow()
        .onStart { _loadingStateLiveData.value = true }
        //2 [Waits for values to stop arriving after a certain period of time]
        .debounce(SEARCH_DELAY_MILLIS)
        //3
        .mapLatest {
            if (it.length >= MIN_QUERY_LENGTH) {
                addSearchHistory(SearchHistory(searchTitle = it))
                fetchNews(it)
            } else {
                emptyList()
            }

        }
        //4
        .catch {
            Log.i("", "${it.message}")
            // Log Error
        }.asLiveData()

    private suspend fun fetchNews(query: String): List<NewsNetworkEntity> {
        var news = arrayListOf<NewsNetworkEntity>()
        newsAppRepository.searchNews(query).collect { resultWrapper ->
            resultWrapper.doIfSuccess {
                news = arrayListOf()
                news.addAll(it)
                _loadingStateLiveData.value = false
            }
            resultWrapper.doIfFailure { _loadingStateLiveData.value = false }
            resultWrapper.doIfNetworkException { _loadingStateLiveData.value = false }
        }
        return if (news.isNullOrEmpty()) ArrayList() else news
    }
}