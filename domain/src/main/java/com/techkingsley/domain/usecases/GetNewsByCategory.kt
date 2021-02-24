package com.techkingsley.domain.usecases

import com.techkingsley.domain.repositories.NewsRepository
import javax.inject.Inject


class GetNewsByCategory @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(category: String) = newsRepository.observeNewsByCategory(category)
}