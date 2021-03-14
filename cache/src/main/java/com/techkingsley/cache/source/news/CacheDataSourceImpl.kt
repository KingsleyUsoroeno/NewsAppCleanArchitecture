package com.techkingsley.cache.source.news

import com.techkingsley.cache.db.NewsDatabase
import com.techkingsley.cache.models.CachedBookMarkNews
import com.techkingsley.cache.models.CachedNews
import com.techkingsley.cache.models.CachedSearchHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(
    private val db: NewsDatabase
) : CacheDataSource {

    override fun observeSearchHistory(): Flow<List<CachedSearchHistory>> {
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

    override suspend fun insertNews(news: List<CachedNews>) {
        db.newsDao().insertAll(news)
    }

    override suspend fun deleteAllNews() {
        db.newsDao().deleteAllNews()
    }

    override suspend fun deleteNews(newsEntity: CachedNews) {
        db.newsDao().delete(newsEntity)
    }

    override suspend fun bookMarkNews(bookMarkNews: CachedBookMarkNews) {
        db.bookMarkedNewsDao().insert(bookMarkNews)
    }

    override suspend fun deleteBookMarkedNews(bookMarkNews: CachedBookMarkNews) {
        db.bookMarkedNewsDao().delete(bookMarkNews)
    }

    override fun observeBookMarkedNews(): Flow<List<CachedBookMarkNews>> {
        return db.bookMarkedNewsDao().observeBookNews
    }

    override suspend fun getAllNewsCountByCategory(newsCategory: String): Int? {
        return db.newsDao().getCount(newsCategory)
    }

    override suspend fun getNewsByCategory(newsCategory: String): List<CachedNews> {
        return db.newsDao().getNewsByCategory(newsCategory)
    }
}