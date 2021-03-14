package com.techkingsley.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techkingsley.presentation.newsstate.NewsUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel<T : NewsUiState>(initialState: T) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(initialState)

    // The UI collects from this StateFlow to get its state updates
    val viewState: SharedFlow<T>
        get() = _uiState


    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    fun setState(newsUiState: T) {
        this._uiState.value = newsUiState
    }
}