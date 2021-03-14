package com.techkingsley.domain.usecases.news

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.models.news.SourcedNews
import com.techkingsley.domain.repositories.NewsRepository
import com.techkingsley.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchTrendingNews @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Nothing, List<SourcedNews>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Nothing?): Flow<List<SourcedNews>> {
        return flow {
            emit(newsRepository.fetchTrendingNews())
        }
    }
}