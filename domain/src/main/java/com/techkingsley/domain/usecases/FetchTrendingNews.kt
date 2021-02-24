package com.techkingsley.domain.usecases

import com.techkingsley.domain.repositories.NewsRepository
import javax.inject.Inject

class FetchTrendingNews @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(category: String, apiKey: String) = newsRepository.fetchTrendingNews(category, apiKey)
}