package com.techkingsley.cache.repository.news

import com.techkingsley.cache.mappers.bookmark.BookMarkNewsMapper
import com.techkingsley.cache.mappers.news.NewsMapper
import com.techkingsley.cache.mappers.search.SearchResultMapper
import com.techkingsley.cache.source.news.CacheDataSource
import com.techkingsley.data.model.BookMarkNewsEntity
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SearchHistoryEntity
import com.techkingsley.data.repository.news.CacheNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val newsMapper: NewsMapper = NewsMapper(),
    private val searchResultMapper: SearchResultMapper = SearchResultMapper(),
    private val bookMarkNewsMapper: BookMarkNewsMapper = BookMarkNewsMapper()
) : CacheNewsRepository {

    override fun getSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return cacheDataSource.getAllSearchHistory().map { cachedSearchHistory ->
            cachedSearchHistory.map { searchResultMapper.mapFromCached(it) }
        }
    }

    override fun observeAllNews(category: String): Flow<List<NewsEntity>> {
        return cacheDataSource.observeAllNews(category).map {
            newsMapper.mapFromEntityList(category = category, entities = it)
        }
    }

    override suspend fun addSearchHistory(searchHistory: SearchHistoryEntity) {
        this.cacheDataSource.addSearchHistory(searchResultMapper.mapToCached(searchHistory))
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity) {
        this.cacheDataSource.deleteSearchHistory(searchResultMapper.mapToCached(searchHistory))
    }

    override suspend fun insertNews(category: String, newsEntity: List<NewsEntity>) {
        val news = newsEntity.map { newsMapper.mapToCached(category, it) }
        this.cacheDataSource.insertNews(news)
    }

    override suspend fun deleteAllNews() = cacheDataSource.deleteAllNews()

    override suspend fun deleteNews(category: String, newsEntity: NewsEntity) {
        cacheDataSource.deleteNews(newsMapper.mapToCached(category = category, type = newsEntity))
    }

    override suspend fun bookMarkNews(bookMarkNewsEntity: BookMarkNewsEntity) {
        this.cacheDataSource.bookMarkNews(bookMarkNewsMapper.mapToCached(bookMarkNewsEntity))
    }

    override suspend fun deleteBookMarkedNews(bookMarkNewsEntity: BookMarkNewsEntity) {
        this.cacheDataSource.deleteBookMarkedNews(bookMarkNewsMapper.mapToCached(bookMarkNewsEntity))
    }

    override fun getAllBookMarkedNews(): Flow<List<BookMarkNewsEntity>> {
        return this.cacheDataSource.getBookMarkedNews().map { cachedBookMarkedNews ->
            cachedBookMarkedNews.map { bookMarkNewsMapper.mapFromCached(it) }
        }
    }
}