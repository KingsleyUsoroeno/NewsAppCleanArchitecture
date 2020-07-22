package com.techkingsley.newsappcleanarchitecture.framework.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techkingsley.newsappcleanarchitecture.business.data.cache.repository.NewsAppRepository
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.SourceNewsResponse
import com.techkingsley.newsappcleanarchitecture.business.domain.state.DataState
import com.techkingsley.newsappcleanarchitecture.business.interactors.ResultWrapper
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel @ViewModelInject constructor(private val newsAppRepository: NewsAppRepository) : ViewModel() {

    private val _dataState: MutableLiveData<ResultWrapper<SourceNewsResponse>> = MutableLiveData()

    private val _dataStateWithDataState: MutableLiveData<DataState<SourceNewsResponse>> = MutableLiveData()

    val dataState: LiveData<DataState<SourceNewsResponse>>
        get() = _dataStateWithDataState

    init {
        //refreshDataFromRepository()
    }


    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                newsAppRepository.refreshTechNews("tech")
                /*_eventNetworkError.value = false
                _isNetworkErrorShown.value = false*/

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                /*if (playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true*/
            }
        }
    }


}