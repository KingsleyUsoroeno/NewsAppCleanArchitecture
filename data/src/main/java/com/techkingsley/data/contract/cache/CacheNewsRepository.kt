package com.techkingsley.data.contract.cache


import com.techkingsley.data.model.BookMarkNewsEntity
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface CacheNewsRepository {

    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    fun observeAllNews(category: String): Flow<List<NewsEntity>>

    suspend fun addSearchHistory(searchHistory: SearchHistoryEntity)

    suspend fun deleteSearchHistory(searchHistory: SearchHistoryEntity)

    suspend fun insertNews(category: String, newsEntity: List<NewsEntity>)

    suspend fun deleteAllNews()

    suspend fun deleteNews(category: String, newsEntity: NewsEntity)

    suspend fun bookMarkNews(bookMarkNewsEntity: BookMarkNewsEntity)

    suspend fun deleteBookMarkedNews(bookMarkNewsEntity: BookMarkNewsEntity)

    fun getAllBookMarkedNews(): Flow<List<BookMarkNewsEntity>>

    suspend fun isNewsCached(newsCategory: String): Boolean

    suspend fun getNewsByCategory(newsCategory: String): List<NewsEntity>
}