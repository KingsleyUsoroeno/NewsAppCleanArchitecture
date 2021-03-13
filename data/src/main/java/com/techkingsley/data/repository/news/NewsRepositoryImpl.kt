package com.techkingsley.data.repository.news

import com.techkingsley.data.contract.cache.CacheNewsRepository
import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.data.mapper.NewsMapper
import com.techkingsley.data.mapper.SearchedNewsMapper
import com.techkingsley.data.mapper.SourceNewsMapper
import com.techkingsley.data.state.Result
import com.techkingsley.domain.entities.News
import com.techkingsley.domain.entities.SearchHistory
import com.techkingsley.domain.entities.SourcedNews
import com.techkingsley.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val cacheNewsRepository: CacheNewsRepository,
    private val newsRemoteRepository: NewsRemoteRepository,
    private val newsMapper: NewsMapper = NewsMapper(),
    private val searchHistoryMapper: SearchedNewsMapper = SearchedNewsMapper(),
    private val sourceNewsMapper: SourceNewsMapper = SourceNewsMapper()
) : NewsRepository {

    override fun getSearchHistory(): Flow<List<SearchHistory>> {
        return this.cacheNewsRepository.getSearchHistory().map { search ->
            search.map { searchHistoryMapper.mapToDomain(it) }
        }
    }

    override suspend fun insertSearchHistory(searchHistory: SearchHistory) {
        this.cacheNewsRepository.addSearchHistory(
            searchHistory = searchHistoryMapper.mapFromDomain(searchHistory)
        )
    }

    override suspend fun insertNews(news: News) {
        throw UnsupportedOperationException()
    }

    override suspend fun insertAllNews(category: String, news: List<News>) {
        val newsEntity = news.map { newsMapper.mapFromDomain(it) }
        this.cacheNewsRepository.insertNews(category, newsEntity)
    }

    override suspend fun deleteNews(news: News) {
        this.cacheNewsRepository.deleteNews(news.category, newsMapper.mapFromDomain(news))
    }

    override suspend fun deleteAllNews() {
        this.cacheNewsRepository.deleteAllNews()
    }

    override fun observeNewsByCategory(category: String): Flow<List<News>> {
        return this.cacheNewsRepository.observeAllNews(category).map { newsEntity ->
            newsEntity.map { newsMapper.mapToDomain(it) }
        }
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        this.cacheNewsRepository.deleteSearchHistory(
            searchHistory =
            searchHistoryMapper.mapFromDomain(searchHistory)
        )
    }

    override suspend fun fetchTechNews(category: String, from: String) {
        when (val result = newsRemoteRepository.fetchTechNews(category, from)) {
            is Result.Success -> {
                val news = result.data.map { newsMapper.mapToDomain(it) }
                insertAllNews(category, news)
            }

            is Result.Error -> {
                throw Exception(result.exception)
            }
        }
    }

    override fun fetchTrendingNews(): Flow<List<SourcedNews>> {
        return flow {
            when (val result = newsRemoteRepository.fetchTrendingNews()) {
                is Result.Loading -> {
                    emit(emptyList())
                }

                is Result.Success -> {
                    emit(result.data.map { sourceNewsMapper.mapToDomain(it) })
                }

                is Result.Error -> {
                    throw Exception(result.exception)
                }
            }
        }
    }

    override suspend fun fetchPoliticalNews(category: String, from: String) {
        when (val result = newsRemoteRepository.fetchPoliticalNews(category, from)) {
            is Result.Success -> {
                cacheNewsRepository.insertNews(category, result.data)
            }

            is Result.Error -> {
                throw Exception(result.exception)
            }
        }
    }

    override suspend fun fetchMovieNews(category: String, from: String) {
        when (val result = newsRemoteRepository.fetchMovieNews(category, from)) {
            is Result.Success -> {
                cacheNewsRepository.insertNews(category, result.data)
            }

            is Result.Error -> {
                throw Exception(result.exception)
            }
        }
    }

    override suspend fun searchNews(category: String, from: String): List<News> {
        return when (val result = newsRemoteRepository.searchNews(category, from)) {
            is Result.Loading -> {
                emptyList()
            }

            is Result.Success -> {
                result.data.map { newsMapper.mapToDomain(it) }
            }

            is Result.Error -> {
                throw Exception(result.exception)
            }
        }
    }
}