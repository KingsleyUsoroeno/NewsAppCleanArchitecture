package com.techkingsley.presentation.technology

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techkingsley.domain.usecases.news.FetchTechNews
import com.techkingsley.domain.usecases.news.GetNewsByCategory
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.utils.NewsConstants
import com.techkingsley.presentation.utils.from
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TechnologyNewsViewModel @Inject constructor(
    private val getNewsByCategory: GetNewsByCategory,
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
            try {
                fetchTechNews.invoke(NewsConstants.TECH_NEWS, Date().from())
                _uiState.value = NewsUiState.Loading
                getNewsByCategory.execute(NewsConstants.TECH_NEWS).collect {
                    if (it.isNullOrEmpty().not()) {
                        _uiState.value = NewsUiState.Success(it)
                    } else {
                        _uiState.value = NewsUiState.Error("Failed to load your tech news")
                    }
                }
            } catch (e: Exception) {
                val message: String = if (e is SocketTimeoutException) "Please check your internet connection and try again" else e.message ?: "something went wrong"
                _uiState.value = NewsUiState.Error(message)
            }
        }
    }
}