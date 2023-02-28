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
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var currentCategory: String = NewsConstants.TRENDING_NEWS

    private val _state = MutableStateFlow(NewsUiState(selectedNewsCategory = currentCategory))
    val state: StateFlow<NewsUiState> = _state

    init {
        fetchNews()
    }

    private fun fetchNews() {
        newsRepository.observeNewsByCategory(currentCategory, "2023-01-28")
            .onStart {
                _state.value = _state.value.setLoading()
            }.onEach { news ->
                _state.value = _state.value.setData(news)
            }.catch { throwable ->
                _state.value = _state.value.setError(throwable)
            }.launchIn(viewModelScope)
    }


    fun onNewsCategorySelected(category: String) {
        if (_state.value.selectedNewsCategory == category) return

        viewModelScope.launch {
            val news = newsRepository.getNewsByCategory(category)
            _state.value = _state.value.copy(news = news, selectedNewsCategory = category)
        }
    }

    fun toggleNewsBookmarkStatus(news: News) {
        viewModelScope.launch {
            newsRepository.saveOrRemoveNewsFromBookmarks(news)
            val newsByCategory = newsRepository.getNewsByCategory(news.category)
            _state.value = _state.value.copy(news = newsByCategory)
        }
    }
}