package com.techkingsley.presentation.politics

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.domain.usecases.news.FetchPoliticalNews
import com.techkingsley.domain.usecases.news.GetNewsByCategory
import com.techkingsley.presentation.utils.NewsConstants
import com.techkingsley.presentation.utils.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PoliticalNewsViewModel @ViewModelInject constructor(
    private val fetchPoliticalNews: FetchPoliticalNews,
    private val getAllNews: GetNewsByCategory
) : ViewModel() {

    private var _eventNetworkError = MutableLiveData<Boolean>()

    private val newsCategory: String = NewsConstants.POLITICAL_NEWS

    val isNetworkErrorLiveData: LiveData<Boolean>
        get() = _eventNetworkError

    init {
        fetchPoliticalNews()
    }

    val politicalNews = getAllNews.invoke(newsCategory).asLiveData()

    private fun fetchPoliticalNews() = viewModelScope.launch(Dispatchers.IO) {
        try {
            fetchPoliticalNews.invoke(newsCategory, Date().from())

        } catch (e: Exception) {
            Log.i("PoliticalNewsViewModel", "exception caught is $e")
            // Show a Toast error message and hide the progress bar.
            if (politicalNews.value.isNullOrEmpty())
                _eventNetworkError.postValue(true)
        }
    }
}