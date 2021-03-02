package com.techkingsley.domain.usecases

import com.techkingsley.domain.repositories.NewsRepository
import javax.inject.Inject

class GetAllSearchHistory @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke() = newsRepository.getSearchHistory()
}