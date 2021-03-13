package com.techkingsley.presentation.technology

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techkingsley.domain.entities.params.GetNewsParams
import com.techkingsley.domain.usecases.news.FetchTechNews
import com.techkingsley.domain.usecases.news.ObserveNewsByCategory
import com.techkingsley.presentation.from
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.utils.NewsConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TechnologyNewsViewModel @Inject constructor(
    private val observeNews: ObserveNewsByCategory,
    private val fetchTechNews: FetchTechNews
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Idle)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        fetchTechNews()
    }

    private fun fetchTechNews() {
        viewModelScope.launch {
            fetchTechNews.execute(GetNewsParams(NewsConstants.TECH_NEWS, Date().from()))
                .map {
                    if (it.isNullOrEmpty().not()) {
                        _uiState.value = NewsUiState.Success(it)
                    } else {
                        _uiState.value = NewsUiState.Error("Failed to load your tech news")
                    }
                }
                .onStart {
                    _uiState.value = NewsUiState.Loading
                }
                .catch { error ->
                    val message: String =
                        if (error is SocketTimeoutException) "Please check your internet connection and try again"
                        else error.message ?: "something went wrong"
                    println("VM error is ${error.message}")
                    _uiState.value = NewsUiState.Error(message)

                }.collect {}
        }
    }
}