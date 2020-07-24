package com.techkingsley.newsappcleanarchitecture.framework.datasource.cache

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface LocalCacheDataSource {

    suspend fun insertNews(news: News)

    suspend fun insertAllNews(news: List<News>)

    fun observeSearchHistory(): Flow<List<SearchHistory>>

    suspend fun addSearchHistory(searchHistory: SearchHistory)

    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    fun observeAllNews(): Flow<List<News>>

    fun observeAllNewsByCategory(category: String): Flow<List<News>>

}