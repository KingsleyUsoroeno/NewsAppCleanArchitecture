package com.techkingsley.domain.usecases.news

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.exception.requireParams
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.repositories.NewsRepository
import com.techkingsley.domain.usecases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNews @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<String, List<News>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: String?): Flow<List<News>> {
        requireParams(params)
        return newsRepository.observeNews(params)
    }
}