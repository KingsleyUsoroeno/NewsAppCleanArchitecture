package com.techkingsley.domain.usecases.news

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.base.FlowUseCase
import com.techkingsley.domain.entities.news.SourcedNews
import com.techkingsley.domain.repositories.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTrendingNews @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Nothing, List<SourcedNews>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Nothing?): Flow<List<SourcedNews>> {
        return newsRepository.fetchTrendingNews()
    }
}