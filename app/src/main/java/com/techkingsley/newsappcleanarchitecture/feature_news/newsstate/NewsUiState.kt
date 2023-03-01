package com.techkingsley.newsappcleanarchitecture.feature_news.newsstate

import com.techkingsley.domain.models.news.News

data class NewsUiState(
    val isLoading: Boolean = false,
    val news: List<News> = emptyList(),
    val throwable: Throwable? = null,
    val selectedNewsCategory: String
) {
    fun setLoading(): NewsUiState = copy(isLoading = true)

    fun setData(news: List<News>) = copy(isLoading = false, news = news)

    fun setError(throwable: Throwable?) = copy(
        isLoading = false, throwable = throwable
    )
}