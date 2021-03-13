package com.techkingsley.domain.usecases.news

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.base.FlowUseCase
import com.techkingsley.domain.entities.news.News
import com.techkingsley.domain.entities.params.GetNewsParams
import com.techkingsley.domain.exception.requireParams
import com.techkingsley.domain.repositories.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMovieNews @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<GetNewsParams, List<News>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: GetNewsParams?): Flow<List<News>> {
        requireParams(params)
        return newsRepository.fetchNewsByCategory(params.category, params.from)
    }
}