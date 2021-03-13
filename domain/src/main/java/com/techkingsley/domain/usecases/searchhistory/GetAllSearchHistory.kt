package com.techkingsley.domain.usecases.searchhistory

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.base.FlowUseCase
import com.techkingsley.domain.entities.SearchHistory
import com.techkingsley.domain.repositories.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSearchHistory @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Nothing, List<SearchHistory>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Nothing?): Flow<List<SearchHistory>> {
        return newsRepository.getSearchHistory()
    }
}