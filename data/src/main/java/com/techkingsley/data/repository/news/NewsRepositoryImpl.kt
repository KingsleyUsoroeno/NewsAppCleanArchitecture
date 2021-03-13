package com.techkingsley.data.repository.news

import com.techkingsley.data.contract.cache.CacheNewsRepository
import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.data.mapper.NewsMapper
import com.techkingsley.data.mapper.SearchedNewsMapper
import com.techkingsley.data.mapper.SourceNewsMapper
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.domain.entities.news.News
import com.techkingsley.domain.entities.news.SourcedNews
import com.techkingsley.domain.entities.searchhistory.SearchHistory
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

    override fun fetchNewsByCategory(category: String, from: String): Flow<List<News>> {
        return flow {
            val cachedNews: List<NewsEntity> = cacheNewsRepository.getNewsByCategory(category)
            if (cacheNewsRepository.isNewsCached(category)) {
                emit(newsMapper.mapToDomainList(cachedNews))
            } else {
                val news: List<NewsEntity> = newsRemoteRepository.fetchNewsByCategory(category, from)
                cacheNewsRepository.insertNews(category, news)
                emit(newsMapper.mapToDomainList(cacheNewsRepository.getNewsByCategory(category)))
            }
        }
    }

    override fun fetchTrendingNews(): Flow<List<SourcedNews>> {
        return flow {
            val sourcedNews: List<SourceNewsEntity> = newsRemoteRepository.fetchTrendingNews()
            emit(sourcedNews.map { sourceNewsMapper.mapToDomain(it) })
        }
    }

    override suspend fun searchNews(category: String, from: String): Flow<List<News>> {
        return flow {
            val news: List<NewsEntity> = newsRemoteRepository.fetchNewsByCategory(category, from)
            emit(newsMapper.mapToDomainList(news))
        }
    }
}