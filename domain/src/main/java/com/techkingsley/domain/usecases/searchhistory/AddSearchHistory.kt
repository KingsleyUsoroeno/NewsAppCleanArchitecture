package com.techkingsley.domain.usecases.searchhistory

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.domain.repositories.NewsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddSearchHistory @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(searchHistory: SearchHistory) {
        withContext(postExecutionThread.io) {
            newsRepository.insertSearchHistory(searchHistory)
        }
    }
}