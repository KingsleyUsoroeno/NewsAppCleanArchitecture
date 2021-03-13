package com.techkingsley.newsappcleanarchitecture.framework.ui.movies

import androidx.lifecycle.*
import com.techkingsley.domain.entities.News
import com.techkingsley.domain.usecases.news.FetchMovieNews
import com.techkingsley.domain.usecases.news.GetNewsByCategory
import com.techkingsley.presentation.utils.NewsConstants
import com.techkingsley.presentation.utils.from
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val fetchMovieNews: FetchMovieNews,
    private val getNewsByCategory: GetNewsByCategory
) : ViewModel() {

    private var _eventNetworkError = MutableLiveData<String>()

    private val newsCategory: String = NewsConstants.MOVIE_NEWS

    val errorLiveData: LiveData<String>
        get() = _eventNetworkError

    val news: LiveData<List<News>>
        get() = getNewsByCategory.invoke(newsCategory).asLiveData()

    init {
        fetchNews()
    }

    private fun fetchNews() = viewModelScope.launch(Dispatchers.IO) {
        try {
            fetchMovieNews.invoke(category = newsCategory, from = Date().from())

        } catch (exception: Exception) {
            if (news.value.isNullOrEmpty()) {
                if (exception is SocketTimeoutException) {
                    _eventNetworkError.postValue("Please check your internet connection and try again")

                } else {
                    _eventNetworkError.postValue(exception.message)
                }
            }
        }
    }
}