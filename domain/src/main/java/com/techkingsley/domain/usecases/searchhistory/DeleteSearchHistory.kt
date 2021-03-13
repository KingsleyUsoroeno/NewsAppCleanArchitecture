package com.techkingsley.domain.usecases.searchhistory

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.entities.SearchHistory
import com.techkingsley.domain.repositories.NewsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteSearchHistory @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(searchHistory: SearchHistory) = withContext(postExecutionThread.io) {
        newsRepository.deleteSearchHistory(searchHistory)
    }
}