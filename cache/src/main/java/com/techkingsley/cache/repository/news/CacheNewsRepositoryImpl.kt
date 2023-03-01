package com.techkingsley.cache.repository.news

import com.techkingsley.cache.NewsDatabase
import com.techkingsley.cache.mappers.CacheNewsMapper
import com.techkingsley.cache.mappers.CacheSearchResultMapper
import com.techkingsley.data.contract.cache.CacheNewsRepository
import com.techkingsley.data.mapper.SearchedNewsMapper
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SearchHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CacheNewsRepositoryImpl @Inject constructor(
    private val newsMapper: CacheNewsMapper = CacheNewsMapper(),
    private val searchResultMapper: CacheSearchResultMapper = CacheSearchResultMapper(),
    private val newsDatabase: NewsDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CacheNewsRepository {

    private val searchHistoryDao by lazy { newsDatabase.searchHistoryDao() }
    private val newsDao by lazy { newsDatabase.newsDao() }

    override fun observeSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return searchHistoryDao.observeSearchHistory().map { searchResultMapper.mapToEntityList(it) }
    }

    override val newsObserver: Flow<List<NewsEntity>>
        get() = newsDao.observeNews.map { newsMapper.mapToEntityList(it) }


    override suspend fun hasCachedNewsInDb(): Boolean = newsDao.getTotalNewsCount() > 0

    override fun observeAllNews(category: String): Flow<List<NewsEntity>> {
        return newsDao.observeNewsByCategory(category).map {
            newsMapper.mapToEntityList(it)
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
                    urlToImage = it.urlToImage,
                    bookmarkedTimestamp = it.bookmarkedTimestamp
                )
            }
        }
    }

    override suspend fun addSearchHistory(searchHistory: SearchHistoryEntity) {
        withContext(ioDispatcher) {
            searchHistoryDao.insert(searchResultMapper.mapToCached(searchHistory))
        }
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity) {
        withContext(ioDispatcher) {
            searchHistoryDao.delete(searchResultMapper.mapToCached(searchHistory))
        }
    }

    override suspend fun insertNews(newsEntity: List<NewsEntity>) {
        withContext(ioDispatcher) {
            newsDao.insertAll(newsMapper.mapToCacheList(newsEntity))
        }
    }

    override suspend fun deleteAllNews() = newsDao.deleteAllNews()

    override suspend fun deleteNews(newsId: Long) {
        withContext(ioDispatcher) { newsDao.deleteNewsById(newsId) }
    }

    override suspend fun bookMarkNews(news: NewsEntity) {
        withContext(ioDispatcher) {
            var cacheNewsEntity = newsMapper.mapToCached(news)
            cacheNewsEntity = cacheNewsEntity.copy(isBookmarked = true, bookmarkedTimestamp = news.bookmarkedTimestamp)
            newsDao.insert(cacheNewsEntity)
        }
    }

    override suspend fun removeNewsBookmarkStatus(news: NewsEntity) {
        withContext(ioDispatcher) {
            var cacheNewsEntity = newsMapper.mapToCached(news)
            cacheNewsEntity = cacheNewsEntity.copy(isBookmarked = false, bookmarkedTimestamp = news.bookmarkedTimestamp)
            newsDao.insert(cacheNewsEntity)
        }
    }

    override val observeAllBookmarkedNews: Flow<List<NewsEntity>>
        get() = newsDao.observeBookmarkedNews().map { cacheNewsEntities ->
            newsMapper.mapToEntityList(cacheNewsEntities)
        }

    override suspend fun getNewsByCategory(newsCategory: String): List<NewsEntity> {
        return newsDao.getNewsByCategory(newsCategory).map { cacheNewsEntities ->
            newsMapper.mapFromCached(cacheNewsEntities)
        }
    }
}