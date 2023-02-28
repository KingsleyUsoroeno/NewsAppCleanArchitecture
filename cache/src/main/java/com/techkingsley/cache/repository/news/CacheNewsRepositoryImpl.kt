package com.techkingsley.cache.repository.news

import com.techkingsley.cache.NewsDatabase
import com.techkingsley.cache.mappers.CacheMappers
import com.techkingsley.data.contract.cache.CacheNewsRepository
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SearchHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CacheNewsRepositoryImpl @Inject constructor(
    private val cacheMappers: CacheMappers,
    private val newsDatabase: NewsDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CacheNewsRepository {

    private val searchHistoryDao by lazy { newsDatabase.searchHistoryDao() }
    private val newsDao by lazy { newsDatabase.newsDao() }

    override fun getSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return searchHistoryDao.observeSearchHistory().map { cachedSearchHistory ->
            cachedSearchHistory.map { cacheMappers.searchResultMapper.mapFromCached(it) }
        }
    }

    override val newsObserver: Flow<List<NewsEntity>>
        get() = newsDao.observeNews.map { cacheMappers.newsMapper.mapToEntityList(it) }

    override fun observeAllNews(category: String): Flow<List<NewsEntity>> {
        return newsDao.observeNewsByCategory(category).map {
            cacheMappers.newsMapper.mapToEntityList(it)
        }
    }

    override fun observeBookmarkedNews(): Flow<List<NewsEntity>> {
        return newsDao.observeBookmarkedNews().map { cacheNews ->
            cacheNews.map {
                NewsEntity(
                    id = it.id,
                    isBookmarked = true,
                    description = it.description,
                    title = it.title,
                    newsUrl = it.newsUrl,
                    author = it.author,
                    category = it.category,
                    urlToImage = it.urlToImage
                )
            }
        }
    }

    override suspend fun addSearchHistory(searchHistory: SearchHistoryEntity) {
        withContext(ioDispatcher) {
            searchHistoryDao.insert(cacheMappers.searchResultMapper.mapToCached(searchHistory))
        }
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity) {
        withContext(ioDispatcher) {
            searchHistoryDao.delete(cacheMappers.searchResultMapper.mapToCached(searchHistory))
        }
    }

    override suspend fun insertNews(newsEntity: List<NewsEntity>) {
        withContext(ioDispatcher) {
            newsDao.insertAll(cacheMappers.newsMapper.mapToCacheList(newsEntity))
        }
    }

    override suspend fun deleteAllNews() = newsDao.deleteAllNews()

    override suspend fun deleteNews(newsId: Long) {
        withContext(ioDispatcher) { newsDao.deleteNewsById(newsId) }
    }

    override suspend fun bookMarkNews(newsId: Long) {
        withContext(ioDispatcher) { newsDao.toggleNewsBookmarkStatus(true, newsId) }
    }

    override suspend fun removeNewsBookmarkStatus(newsId: Long) {
        withContext(ioDispatcher) { newsDao.toggleNewsBookmarkStatus(false, newsId) }
    }

    override fun getAllBookMarkedNews(): Flow<List<NewsEntity>> {
        return newsDao.observeBookmarkedNews().map { cacheNewsEntities ->
            cacheMappers.newsMapper.mapToEntityList(cacheNewsEntities)
        }
    }

    override suspend fun getNewsByCategory(newsCategory: String): List<NewsEntity> {
        return newsDao.getNewsByCategory(newsCategory).map { cacheNewsEntities ->
            cacheMappers.newsMapper.mapFromCached(cacheNewsEntities)
        }
    }

    override suspend fun getTotalNewsCount(): Int {
        return newsDao.getTotalNewsCount()
    }
}