package com.techkingsley.newsappcleanarchitecture.framework.ui.movies


import androidx.lifecycle.ViewModel

//import com.techkingsley.cache.repository.NewsAppRepository

class MovieViewModel : ViewModel()

//class MovieViewModel @ViewModelInject constructor(private val newsAppRepository: NewsAppRepository) : ViewModel() {
//
//    companion object {
//        const val NEWS_CATEGORY = "movies"
//    }
//
//    val movieNews = newsAppRepository.observeAllNews(NEWS_CATEGORY).asLiveData()
//
//    private var _eventNetworkError = MutableLiveData<Boolean>()
//
//    val isNetworkErrorLiveData: LiveData<Boolean>
//        get() = _eventNetworkError
//
//    init {
//        refreshDataFromRepository()
//    }
//
//    private fun refreshDataFromRepository() = viewModelScope.launch {
//        try {
//            newsAppRepository.refreshMovieNews(NEWS_CATEGORY)
//            _eventNetworkError.value = false
//            _eventNetworkError.value = false
//
//        } catch (networkError: IOException) {
//            // Show a Toast error message and hide the progress bar.
//            if (movieNews.value.isNullOrEmpty())
//                _eventNetworkError.value = true
//        }
//    }
//}