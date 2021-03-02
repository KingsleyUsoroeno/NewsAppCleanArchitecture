package com.techkingsley.presentation.technology

import androidx.lifecycle.*
import com.techkingsley.domain.entities.News
import com.techkingsley.domain.usecases.FetchTechNews
import com.techkingsley.domain.usecases.GetNewsByCategory
import com.techkingsley.presentation.utils.NewsConstants
import com.techkingsley.presentation.utils.from
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TechnologyNewsViewModel @Inject constructor(
    private val getNewsByCategory: GetNewsByCategory,
    private val fetchTechNews: FetchTechNews
) : ViewModel() {

    val cachedTechNews: LiveData<List<News>>
        get() = getNewsByCategory.invoke(NewsConstants.TECH_NEWS).asLiveData()


    private var _eventNetworkError = MutableLiveData<String>()

    val networkError: LiveData<String>
        get() = _eventNetworkError

    init {
        fetchTechNews()
    }

    private fun fetchTechNews() = viewModelScope.launch(Dispatchers.IO) {
        try {
            fetchTechNews.invoke(NewsConstants.TECH_NEWS, Date().from())
        } catch (e: Exception) {
            // Show a Toast error message and hide the progress bar.
            if (cachedTechNews.value.isNullOrEmpty()) {
                val message: String = if (e is SocketTimeoutException) "Please check your internet connection and try again" else e.message ?: "something went wrong"
                _eventNetworkError.postValue(message)
            }
        }
    }
}