package com.techkingsley.data.repository.news

import com.techkingsley.data.contract.cache.CacheNewsRepository
import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.data.mapper.NewsMapper
import com.techkingsley.data.mapper.SearchedNewsMapper
import com.techkingsley.data.mapper.SourceNewsMapper
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.domain.models.NewsResult
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.models.news.SourcedNews
import com.techkingsley.domain.models.searchhistory.SearchHistory
import com.techkingsley.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val cacheNewsRepository: CacheNewsRepository,
    private val newsRemoteRepository: NewsRemoteRepository,
    private val newsMapper: NewsMapper,
    private val searchHistoryMapper: SearchedNewsMapper,
    private val sourceNewsMapper: SourceNewsMapper
) : NewsRepository {

    override fun observeSearchHistory(): Flow<List<SearchHistory>> {
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

    override fun getNewsByCategory(category: String, from: String): Flow<NewsResult> {
        return flow {
            fetchNewsAndCache(category, from)
            emitAll(cacheNewsRepository.observeAllNews(category).map { news ->
                NewsResult(newsMapper.mapToDomainList(news))
            })
        }.catch { cause: Throwable ->
            val cachedNews = cacheNewsRepository.getNewsByCategory(category)
            emit(NewsResult(newsMapper.mapToDomainList(cachedNews), cause))
        }
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        this.cacheNewsRepository.deleteSearchHistory(
            searchHistory =
            searchHistoryMapper.mapFromDomain(searchHistory)
        )
    }

    override suspend fun fetchNewsByCategory(category: String, from: String): List<News> {
        val news: List<NewsEntity> = newsRemoteRepository.fetchNewsByCategory(category, from)
        return newsMapper.mapToDomainList(news)
    }

    override suspend fun fetchTrendingNews(): List<SourcedNews> {
        val trendingNews: List<SourceNewsEntity> = newsRemoteRepository.fetchTrendingNews()
        return sourceNewsMapper.mapToDomainList(trendingNews)
    }

    override suspend fun searchNews(category: String, from: String): Flow<List<News>> {
        return flow {
            val news: List<NewsEntity> = newsRemoteRepository.fetchNewsByCategory(category, from)
            emit(newsMapper.mapToDomainList(news))
        }
    }

    override fun observeNews(category: String): Flow<List<News>> {
        return cacheNewsRepository.observeAllNews(category).map { newsMapper.mapToDomainList(it) }
    }

    private suspend fun fetchNewsAndCache(category: String, from: String) {
        try {
            val news = fetchNewsByCategory(category = category, from = from)
            if (news.isNullOrEmpty().not()) {
                cacheNewsRepository.insertNews(category, newsMapper.mapFromDomainList(news))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}