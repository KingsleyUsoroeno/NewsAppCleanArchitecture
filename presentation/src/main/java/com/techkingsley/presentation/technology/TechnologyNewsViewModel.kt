package com.techkingsley.presentation.technology

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.techkingsley.domain.usecases.FetchTechNews
import com.techkingsley.domain.usecases.GetNewsByCategory
import kotlinx.coroutines.launch

class TechnologyNewsViewModel @ViewModelInject constructor(
    private val getNewsByCategory: GetNewsByCategory,
    private val fetchTechNews: FetchTechNews
) : ViewModel() {

    companion object {
        const val NEWS_CATEGORY = "tech"
    }

    val techNews = getNewsByCategory.invoke(NEWS_CATEGORY).asLiveData()

    private var _eventNetworkError = MutableLiveData<Boolean>()

    init {
        refreshDataFromRepository()
    }

    val isNetworkErrorLiveData: LiveData<Boolean>
        get() = _eventNetworkError

    private fun refreshDataFromRepository() = viewModelScope.launch {
        try {
            fetchTechNews.invoke(NEWS_CATEGORY, "", "", "")
            _eventNetworkError.value = false
            _eventNetworkError.value = false

        } catch (e: Exception) {
            // Show a Toast error message and hide the progress bar.
            if (techNews.value.isNullOrEmpty())
                _eventNetworkError.value = true
        }
    }
}