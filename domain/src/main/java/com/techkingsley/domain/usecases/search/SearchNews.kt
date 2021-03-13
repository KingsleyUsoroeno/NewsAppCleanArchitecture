package com.techkingsley.domain.usecases.search

import com.techkingsley.domain.repositories.NewsRepository
import javax.inject.Inject

class SearchNews @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(category: String, from: String) = newsRepository.searchNews(category, from)
}