package com.techkingsley.data.repository.news

import com.techkingsley.data.contract.cache.CacheNewsRepository
import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.data.mapper.NewsMapper
import com.techkingsley.data.mapper.SearchedNewsMapper
import com.techkingsley.data.mapper.SourceNewsMapper
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
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
        return cacheNewsRepository.getSearchHistory().map { searchHistoryMapper.mapToDomainList(it) }
    }

    override fun observeBookmarkedNews(): Flow<List<News>> {
        return cacheNewsRepository.observeBookmarkedNews().map { newsMapper.mapToDomainList(it) }
    }

    override suspend fun insertSearchHistory(searchHistory: SearchHistory) {
        cacheNewsRepository.addSearchHistory(searchHistoryMapper.mapFromDomain(searchHistory))
    }

    override suspend fun insertAllNews(news: List<News>) {
        cacheNewsRepository.insertNews(newsMapper.mapFromDomainList(news))
    }

    override suspend fun deleteNews(news: News) {
        cacheNewsRepository.deleteNews(news.id)
    }

    override suspend fun deleteAllNews() {
        cacheNewsRepository.deleteAllNews()
    }

    override fun observeNewsByCategory(category: String, from: String): Flow<List<News>> {
        return flow {
            if (cacheNewsRepository.getTotalNewsCount() > 0) {
                val cachedMovies = cacheNewsRepository.getNewsByCategory(category)
                emit(newsMapper.mapToDomainList(cachedMovies))

            } else {
                val trendingNews = newsRemoteRepository.fetchNewsByCategory("trending", from)
                val movieNews = newsRemoteRepository.fetchNewsByCategory("movies", from)
                val allNews = trendingNews + movieNews
                cacheNewsRepository.insertNews(allNews)

                val cachedMovies = cacheNewsRepository.getNewsByCategory(category)
                emit(newsMapper.mapToDomainList(cachedMovies))
            }
        }.catch { cause: Throwable ->
            if (cacheNewsRepository.getTotalNewsCount() <= 0) throw cause
            emit(newsMapper.mapToDomainList(cacheNewsRepository.getNewsByCategory(category)))
        }
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        cacheNewsRepository.deleteSearchHistory(searchHistoryMapper.mapFromDomain(searchHistory))
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

    override suspend fun getNewsByCategory(category: String): List<News> {
        return newsMapper.mapToDomainList(cacheNewsRepository.getNewsByCategory(category))
    }

    override suspend fun bookmarkNews(newsId: Long) {
        cacheNewsRepository.bookMarkNews(newsId)
    }

    override suspend fun removeNewsFromBookmarks(newsId: Long) {
        cacheNewsRepository.removeNewsBookmarkStatus(newsId)
    }

    override suspend fun saveOrRemoveNewsFromBookmarks(news: News) {
        if (news.isBookmarked) {
            cacheNewsRepository.removeNewsBookmarkStatus(news.id)
        } else {
            cacheNewsRepository.bookMarkNews(news.id)
        }
    }
}