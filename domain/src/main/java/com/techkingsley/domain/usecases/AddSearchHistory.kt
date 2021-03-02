package com.techkingsley.domain.usecases

import com.techkingsley.domain.entities.SearchHistory
import com.techkingsley.domain.repositories.NewsRepository
import javax.inject.Inject

class AddSearchHistory @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(searchHistory: SearchHistory) = newsRepository.insertSearchHistory(searchHistory)
}