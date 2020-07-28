package com.techkingsley.newsappcleanarchitecture.framework.presentation.politics

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.newsappcleanarchitecture.business.data.cache.repository.NewsAppRepository
import kotlinx.coroutines.launch
import java.io.IOException

class PoliticalNewsViewModel @ViewModelInject constructor(private val newsAppRepository: NewsAppRepository) : ViewModel() {

    companion object {
        const val NEWS_CATEGORY = "politics"
    }

    val politicalNews = newsAppRepository.observeAllNews(NEWS_CATEGORY).asLiveData()

    private var _eventNetworkError = MutableLiveData<Boolean>()

    val isNetworkErrorLiveData: LiveData<Boolean>
        get() = _eventNetworkError

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() = viewModelScope.launch {
        try {
            newsAppRepository.refreshPoliticalNews(NEWS_CATEGORY)
            _eventNetworkError.value = false
            _eventNetworkError.value = false

        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
            if (politicalNews.value.isNullOrEmpty())
                _eventNetworkError.value = true
        }
    }

}