package com.techkingsley.domain.usecases

import com.techkingsley.domain.entities.News
import com.techkingsley.domain.repositories.NewsRepository
import javax.inject.Inject

class DeleteNews @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(news: News) = newsRepository.deleteNews(news)
}