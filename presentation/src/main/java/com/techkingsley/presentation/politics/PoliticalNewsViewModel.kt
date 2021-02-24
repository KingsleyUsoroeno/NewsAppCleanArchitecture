package com.techkingsley.presentation.politics

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.domain.usecases.FetchPoliticalNews
import com.techkingsley.domain.usecases.GetNewsByCategory
import kotlinx.coroutines.launch

class PoliticalNewsViewModel @ViewModelInject constructor(
    private val fetchPoliticalNews: FetchPoliticalNews,
    private val getAllNews: GetNewsByCategory
) : ViewModel() {

    companion object {
        const val NEWS_CATEGORY = "politics"
    }

    private var _eventNetworkError = MutableLiveData<Boolean>()

    val isNetworkErrorLiveData: LiveData<Boolean>
        get() = _eventNetworkError

    init {
        fetchPoliticalNews()
    }

    val politicalNews = getAllNews.invoke(NEWS_CATEGORY).asLiveData()

    private fun fetchPoliticalNews() = viewModelScope.launch {
        try {
            fetchPoliticalNews.invoke(NEWS_CATEGORY, "2021-01-24", "publishedAt", "ca4ae9f450a44a39bd7b77f9a8745450")
            _eventNetworkError.value = false
            _eventNetworkError.value = false

        } catch (e: Exception) {
            Log.i("PoliticalNewsViewModel", "exception caught is $e")
            // Show a Toast error message and hide the progress bar.
            if (politicalNews.value.isNullOrEmpty())
                _eventNetworkError.value = true
        }
    }
}