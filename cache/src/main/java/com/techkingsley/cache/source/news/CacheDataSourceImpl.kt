package com.techkingsley.cache.source.news

import com.techkingsley.cache.db.NewsDatabase
import com.techkingsley.cache.model.CachedBookMarkNews
import com.techkingsley.cache.model.CachedNews
import com.techkingsley.cache.model.CachedSearchHistory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(
    private val db: NewsDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CacheDataSource {

    override fun getAllSearchHistory(): Flow<List<CachedSearchHistory>> {
        return db.searchHistoryDao().observeSearchHistory()
    }

    override fun observeAllNews(category: String): Flow<List<CachedNews>> {
        return db.newsDao().observeNewsByCategory(category)
    }

    override suspend fun addSearchHistory(searchHistory: CachedSearchHistory) {
        db.searchHistoryDao().insert(searchHistory)
    }

    override suspend fun deleteSearchHistory(searchHistory: CachedSearchHistory) {
        db.searchHistoryDao().delete(searchHistory)
    }

    override suspend fun insertNews(news: List<CachedNews>) = withContext(dispatcher) {
        db.newsDao().insertAll(news)
    }

    override suspend fun deleteAllNews() = withContext(dispatcher) {
        db.newsDao().deleteAllNews()
    }

    override suspend fun deleteNews(newsEntity: CachedNews) = withContext(dispatcher) {
        db.newsDao().delete(newsEntity)
    }

    override suspend fun bookMarkNews(bookMarkNews: CachedBookMarkNews) {
        withContext(dispatcher) {
            db.bookMarkedNewsDao().insert(bookMarkNews)
        }
    }

    override suspend fun deleteBookMarkedNews(bookMarkNews: CachedBookMarkNews) = withContext(dispatcher) {
        db.bookMarkedNewsDao().delete(bookMarkNews)
    }

    override fun getBookMarkedNews(): Flow<List<CachedBookMarkNews>> {
        return db.bookMarkedNewsDao().getAllBookMarkedNews()
    }
}