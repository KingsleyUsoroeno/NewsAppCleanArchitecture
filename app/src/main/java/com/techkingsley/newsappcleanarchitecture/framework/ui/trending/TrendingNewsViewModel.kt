package com.techkingsley.newsappcleanarchitecture.framework.ui.trending

import androidx.lifecycle.ViewModel

//import androidx.hilt.lifecycle.ViewModelInject
//import androidx.lifecycle.*
//import com.techkingsley.cache.repository.NewsAppRepository
//import kotlinx.coroutines.launch
//import java.io.IOException

class TrendingNewsViewModel : ViewModel()
//
//class TrendingNewsViewModel @ViewModelInject constructor(private val newsAppRepository: NewsAppRepository) : ViewModel() {
//
//    companion object {
//        const val NEWS_CATEGORY = "trending"
//    }
//
//    val trendingNews = newsAppRepository.observeAllNews(NEWS_CATEGORY).asLiveData()
//
//    private var _eventNetworkError = MutableLiveData<Boolean>()
//
//    init {
//        refreshDataFromRepository()
//    }
//
//    val isNetworkErrorLiveData: LiveData<Boolean>
//        get() = _eventNetworkError
//
//    private fun refreshDataFromRepository() {
//        viewModelScope.launch {
//            try {
//                newsAppRepository.refreshTrendingNews("trending")
//                _eventNetworkError.value = false
//                _eventNetworkError.value = false
//
//            } catch (networkError: IOException) {
//                // Show a Toast error message and hide the progress bar.
//                if (trendingNews.value.isNullOrEmpty())
//                    _eventNetworkError.value = true
//            }
//        }
//    }
//}