package com.techkingsley.newsappcleanarchitecture.feature_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.repositories.NewsRepository
import com.techkingsley.newsappcleanarchitecture.feature_news.newsstate.NewsUiState
import com.techkingsley.newsappcleanarchitecture.utils.NewsConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var _currentCategory: String = NewsConstants.TRENDING_NEWS

    private val _newsUiState = MutableStateFlow(NewsUiState(selectedNewsCategory = _currentCategory))
    val newsUiState: StateFlow<NewsUiState> = _newsUiState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        newsRepository.observeNewsByCategory(_currentCategory, "2023-01-28")
            .onStart {
                _newsUiState.value = _newsUiState.value.setLoading()
            }.onEach { news ->
                _newsUiState.value = _newsUiState.value.setData(news)
            }.catch { throwable ->
                _newsUiState.value = _newsUiState.value.setError(throwable)
            }.launchIn(viewModelScope)
    }


    fun onNewsCategorySelected(category: String) {
        if (_newsUiState.value.selectedNewsCategory == category) return

        this._currentCategory = category
        viewModelScope.launch {
            val news = newsRepository.getNewsByCategory(_currentCategory)
            _newsUiState.value = _newsUiState.value.copy(news = news, selectedNewsCategory = _currentCategory)
        }
    }

    fun toggleNewsBookmarkStatus(news: News, onActionCompleted: (isBookmarked: Boolean) -> Unit) {
        viewModelScope.launch {
            newsRepository.saveOrRemoveNewsFromBookmarks(
                news.copy(bookmarkedTimestamp = Date())
            )
            onActionCompleted(!news.isBookmarked)
        }
    }
}