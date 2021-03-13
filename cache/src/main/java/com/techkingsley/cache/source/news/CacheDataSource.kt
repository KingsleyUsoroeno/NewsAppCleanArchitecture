package com.techkingsley.cache.source.news


import com.techkingsley.cache.model.CachedBookMarkNews
import com.techkingsley.cache.model.CachedNews
import com.techkingsley.cache.model.CachedSearchHistory
import kotlinx.coroutines.flow.Flow

interface CacheDataSource {

    fun getAllSearchHistory(): Flow<List<CachedSearchHistory>>

    fun observeAllNews(category: String): Flow<List<CachedNews>>

    suspend fun addSearchHistory(searchHistory: CachedSearchHistory)

    suspend fun deleteSearchHistory(searchHistory: CachedSearchHistory)

    suspend fun insertNews(news: List<CachedNews>)

    suspend fun deleteAllNews()

    suspend fun deleteNews(newsEntity: CachedNews)

    suspend fun bookMarkNews(bookMarkNews: CachedBookMarkNews)

    suspend fun deleteBookMarkedNews(bookMarkNews: CachedBookMarkNews)

    fun getBookMarkedNews(): Flow<List<CachedBookMarkNews>>

    suspend fun getAllNewsCount(newsCategory: String): Int?

    suspend fun getNewsByCategory(newsCategory: String): List<CachedNews>
}