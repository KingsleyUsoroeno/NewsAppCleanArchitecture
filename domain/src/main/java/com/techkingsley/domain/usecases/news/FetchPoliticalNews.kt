package com.techkingsley.domain.usecases.news

import com.techkingsley.domain.repositories.NewsRepository
import javax.inject.Inject

class FetchPoliticalNews @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(category: String, from: String) = newsRepository.fetchPoliticalNews(category, from)
}