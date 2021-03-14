package com.techkingsley.domain.usecases.news

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.exception.requireParams
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.models.params.GetNewsParams
import com.techkingsley.domain.repositories.NewsRepository
import com.techkingsley.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNews @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<GetNewsParams, List<News>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: GetNewsParams?): Flow<List<News>> {
        requireParams(params)
        return flow {
            emit(newsRepository.fetchNewsByCategory(params.category, params.from))
        }
    }
}