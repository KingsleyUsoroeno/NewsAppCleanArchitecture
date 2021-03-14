package com.techkingsley.cache.source.news


import com.techkingsley.cache.models.CachedBookMarkNews
import com.techkingsley.cache.models.CachedNews
import com.techkingsley.cache.models.CachedSearchHistory
import kotlinx.coroutines.flow.Flow

interface CacheDataSource {

    fun observeSearchHistory(): Flow<List<CachedSearchHistory>>

    fun observeAllNews(category: String): Flow<List<CachedNews>>

    suspend fun addSearchHistory(searchHistory: CachedSearchHistory)

    suspend fun deleteSearchHistory(searchHistory: CachedSearchHistory)

    suspend fun insertNews(news: List<CachedNews>)

    suspend fun deleteAllNews()

    suspend fun deleteNews(newsEntity: CachedNews)

    suspend fun bookMarkNews(bookMarkNews: CachedBookMarkNews)

    suspend fun deleteBookMarkedNews(bookMarkNews: CachedBookMarkNews)

    fun observeBookMarkedNews(): Flow<List<CachedBookMarkNews>>

    suspend fun getAllNewsCountByCategory(newsCategory: String): Int?

    suspend fun getNewsByCategory(newsCategory: String): List<CachedNews>
}