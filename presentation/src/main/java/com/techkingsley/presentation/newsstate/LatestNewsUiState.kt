package com.techkingsley.presentation.newsstate

import com.techkingsley.domain.entities.News

sealed class NewsUiState {
    object Idle : NewsUiState()
    object Loading : NewsUiState()
    data class Success(val news: List<News>) : NewsUiState()
    data class Error(val exception: String) : NewsUiState()
}