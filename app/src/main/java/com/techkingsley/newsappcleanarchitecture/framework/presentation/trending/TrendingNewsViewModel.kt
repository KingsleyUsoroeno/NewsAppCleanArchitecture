package com.techkingsley.newsappcleanarchitecture.framework.presentation.trending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.newsappcleanarchitecture.business.data.cache.repository.NewsAppRepository
import kotlinx.coroutines.launch
import java.io.IOException

class TrendingNewsViewModel @ViewModelInject constructor(private val newsAppRepository: NewsAppRepository) : ViewModel() {

    val trendingNews = newsAppRepository.getTrendingNews().asLiveData()

    private var _eventNetworkError = MutableLiveData<Boolean>()

    init {
        refreshDataFromRepository()
    }

    val isNetworkErrorLiveData: LiveData<Boolean>
        get() = _eventNetworkError

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                newsAppRepository.refreshTrendingNews("trending")
                _eventNetworkError.value = false
                _eventNetworkError.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if (trendingNews.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }
}