package com.techkingsley.data.contract.cache


import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface CacheNewsRepository {

    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    fun observeAllNews(category: String): Flow<List<NewsEntity>>

    fun observeBookmarkedNews(): Flow<List<NewsEntity>>

    suspend fun addSearchHistory(searchHistory: SearchHistoryEntity)

    suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity)

    suspend fun insertNews(newsEntity: List<NewsEntity>)

    suspend fun deleteAllNews()

    suspend fun deleteNews(newsId: Long)

    suspend fun bookMarkNews(newsId: Long)

    suspend fun removeNewsBookmarkStatus(newsId: Long)

    fun getAllBookMarkedNews(): Flow<List<NewsEntity>>

    suspend fun getNewsByCategory(newsCategory: String): List<NewsEntity>

    suspend fun getTotalNewsCount(): Int

    val newsObserver: Flow<List<NewsEntity>>
}