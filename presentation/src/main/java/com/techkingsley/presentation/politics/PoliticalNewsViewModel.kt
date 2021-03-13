package com.techkingsley.presentation.politics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techkingsley.domain.entities.params.GetNewsParams
import com.techkingsley.domain.usecases.news.FetchPoliticalNews
import com.techkingsley.domain.usecases.news.ObserveNewsByCategory
import com.techkingsley.presentation.from
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.utils.NewsConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PoliticalNewsViewModel @Inject constructor(
    private val fetchPoliticalNews: FetchPoliticalNews,
    private val observeNews: ObserveNewsByCategory
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Idle)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        fetchPoliticalNews()
    }

    private fun fetchPoliticalNews() {
        viewModelScope.launch {
            fetchPoliticalNews.execute(GetNewsParams(NewsConstants.POLITICAL_NEWS, Date().from()))
                .onStart { _uiState.value = NewsUiState.Loading }
                .map { news ->
                    if (news.isNullOrEmpty().not()) {
                        _uiState.value = NewsUiState.Success(news)
                    } else {
                        _uiState.value = NewsUiState.Error("Failed to load your Political news")
                    }
                }
                .catch { error ->
                    error.printStackTrace()
                    _uiState.value = NewsUiState.Error(error.message ?: "Something went wrong")
                }.collect {}
        }
    }
}