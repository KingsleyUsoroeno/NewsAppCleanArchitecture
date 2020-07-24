package com.techkingsley.newsappcleanarchitecture.framework.datasource.cache

import com.techkingsley.newsappcleanarchitecture.business.data.cache.db.NewsDatabase
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalCacheDataSourceImpl(private val db: NewsDatabase) : LocalCacheDataSource {

    override suspend fun insertNews(news: News) {
        withContext(Dispatchers.IO) {
            db.newsDao().insert(news)
        }
    }

    override suspend fun insertAllNews(news: List<News>) {
        withContext(Dispatchers.IO) {
            db.newsDao().insertAll(news)
        }
    }

    override fun observeSearchHistory(): Flow<List<SearchHistory>> {
        return db.searchHistoryDao().observeSearchHistory()
    }

    override suspend fun addSearchHistory(searchHistory: SearchHistory) {
        withContext(Dispatchers.IO) {
            db.searchHistoryDao().insert(searchHistory)
        }
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) = withContext(Dispatchers.IO) {
        db.searchHistoryDao().delete(searchHistory)
    }

    override fun observeAllNews(): Flow<List<News>> {
        return db.newsDao().observeMovieNews()
    }

    override fun observeAllNewsByCategory(category: String): Flow<List<News>> {
        return db.newsDao().observeNewsByCategory(category)
    }
}