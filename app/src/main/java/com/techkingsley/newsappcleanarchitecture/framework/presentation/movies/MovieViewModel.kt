package com.techkingsley.newsappcleanarchitecture.framework.presentation.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.newsappcleanarchitecture.business.data.cache.repository.NewsAppRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MovieViewModel @ViewModelInject constructor(private val newsAppRepository: NewsAppRepository) : ViewModel() {

    companion object {
        private const val NEWS_CATEGORY = "movies"
    }

    val movieNews = newsAppRepository.observeAllNews(NEWS_CATEGORY).asLiveData()

    private var _eventNetworkError = MutableLiveData<Boolean>()

    val isNetworkErrorLiveData: LiveData<Boolean>
        get() = _eventNetworkError

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() = viewModelScope.launch {
        try {
            newsAppRepository.refreshMovieNews(NEWS_CATEGORY)
            _eventNetworkError.value = false
            _eventNetworkError.value = false

        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
            if (movieNews.value.isNullOrEmpty())
                _eventNetworkError.value = true
        }
    }
}