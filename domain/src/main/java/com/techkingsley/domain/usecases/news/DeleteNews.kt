package com.techkingsley.domain.usecases.news

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.repositories.NewsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteNews @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(news: News) = withContext(postExecutionThread.io) {
        newsRepository.insertNews(news)
    }
}