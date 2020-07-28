package com.techkingsley.newsappcleanarchitecture.framework.presentation.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory
import com.techkingsley.newsappcleanarchitecture.business.data.cache.repository.NewsAppRepository
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.interactors.ResultWrapper
import com.techkingsley.newsappcleanarchitecture.business.interactors.doIfFailure
import com.techkingsley.newsappcleanarchitecture.business.interactors.doIfNetworkException
import com.techkingsley.newsappcleanarchitecture.business.interactors.doIfSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2

@ExperimentalCoroutinesApi
@FlowPreview
class SearchActivityViewModel @ViewModelInject constructor(private val newsAppRepository: NewsAppRepository) : ViewModel() {

    companion object {
        private const val TAG = "SearchActivityViewModel"
    }

    val allSearchHistory = newsAppRepository.getSearchHistory().asLiveData()

    fun addSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        newsAppRepository.addSearchHistory(searchHistory)
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        newsAppRepository.deleteSearchHistory(searchHistory)
    }

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    private val _loadingStateLiveData = MutableLiveData<Boolean>(false)

    private val _searchResult = MutableLiveData<List<NewsNetworkEntity>>()

    private val _errorState = MutableLiveData<Boolean>()

    val loadingState: LiveData<Boolean>
        get() = _loadingStateLiveData

    val searchResult: LiveData<List<NewsNetworkEntity>>
        get() = _searchResult

    val errorState: LiveData<Boolean>
        get() = _errorState

    /*val news = queryChannel
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
        }.asLiveData()*/

    /** Use to Query News From the local database base on the news category passed in */
    fun observeNews(query: String) = newsAppRepository.observeAllNews(query).asLiveData()

    /*its a good practise to log the error and provide the user with a feedback on the UI*/
    fun fetchNews(query: String, hideKeyBoard: () -> Unit) {
        viewModelScope.launch {
            hideKeyBoard()
            _loadingStateLiveData.value = true
            newsAppRepository.searchNews(query).collect { result: ResultWrapper<List<NewsNetworkEntity>> ->
                result.doIfSuccess {
                    _loadingStateLiveData.value = false
                    _searchResult.value = it
                }

                result.doIfFailure { error ->
                    _loadingStateLiveData.value = false
                    //_searchResult.value = observeNews(query).value
                    _searchResult.value = emptyList()
                    Log.i(TAG, "failed to fetch news query due to server error of $error")
                }

                result.doIfNetworkException {
                    _loadingStateLiveData.value = false
                    _searchResult.value = emptyList()
                    Log.i(TAG, "Caught an exception due to ${it.message}")
                }
            }
        }
    }
}