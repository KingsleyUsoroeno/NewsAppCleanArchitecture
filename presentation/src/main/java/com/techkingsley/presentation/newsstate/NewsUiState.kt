package com.techkingsley.presentation.newsstate

import com.techkingsley.domain.models.news.News

sealed class NewsUiState {
    object Idle : NewsUiState()
    object Loading : NewsUiState()
    object Empty : NewsUiState()
    data class Success(val news: List<News>) : NewsUiState()
    data class Error(val exception: String) : NewsUiState()
}