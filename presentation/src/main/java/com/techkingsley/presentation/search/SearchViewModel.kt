package com.techkingsley.presentation.search


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.domain.entities.News
import com.techkingsley.domain.entities.SearchHistory
import com.techkingsley.domain.usecases.news.GetNewsByCategory
import com.techkingsley.domain.usecases.search.SearchNews
import com.techkingsley.domain.usecases.searchhistory.AddSearchHistory
import com.techkingsley.domain.usecases.searchhistory.DeleteSearchHistory
import com.techkingsley.domain.usecases.searchhistory.GetAllSearchHistory
import com.techkingsley.presentation.utils.from
import kotlinx.coroutines.launch
import java.util.*

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2

class SearchViewModel @ViewModelInject constructor(
    private val searchNews: SearchNews,
    private val getAllSearchHistory: GetAllSearchHistory,
    private val addSearchHistory: AddSearchHistory,
    private val getNewsByCategory: GetNewsByCategory,
    private val deleteSearchHistory: DeleteSearchHistory
) : ViewModel() {

    companion object {
        private const val TAG = "SearchActivityViewModel"
    }

    val allSearchHistory = getAllSearchHistory.invoke().asLiveData()

    fun addSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        addSearchHistory.invoke(searchHistory)
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        deleteSearchHistory.invoke(searchHistory)
    }

    //val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    private val _loadingStateLiveData = MutableLiveData<Boolean>(false)

    private val _searchResult = MutableLiveData<List<News>>()

    private val _errorState = MutableLiveData<Boolean>()

    val loadingState: LiveData<Boolean>
        get() = _loadingStateLiveData

    val searchResult: LiveData<List<News>>
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
    fun observeNews(query: String) = getNewsByCategory.invoke(query).asLiveData()

    /*its a good practise to log the error and provide the user with a feedback on the UI*/
    fun fetchNews(query: String, hideKeyBoard: () -> Unit) {
        viewModelScope.launch {
            hideKeyBoard()
            _loadingStateLiveData.value = true
            try {
                val news = searchNews.invoke(query, Date().from())
                if (news.isNullOrEmpty().not()) {
                    _loadingStateLiveData.value = false
                    _searchResult.value = news
                } else {
                    _loadingStateLiveData.value = false
                }

            } catch (e: Exception) {
                _loadingStateLiveData.value = false
                Log.i(TAG, "exception caught searching news is $e")
            }
        }
    }
}