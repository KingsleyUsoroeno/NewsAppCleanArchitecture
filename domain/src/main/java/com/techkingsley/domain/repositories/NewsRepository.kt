package com.techkingsley.domain.repositories

import com.techkingsley.domain.entities.news.News
import com.techkingsley.domain.entities.news.SourcedNews
import com.techkingsley.domain.entities.searchhistory.SearchHistory
import kotlinx.coroutines.flow.Flow

/** An abstract definition of a bookRepository used when communicating with the data layer*/
interface NewsRepository {

    fun getSearchHistory(): Flow<List<SearchHistory>>

    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    suspend fun insertNews(news: News)

    suspend fun insertAllNews(category: String, news: List<News>)

    suspend fun deleteNews(news: News)

    suspend fun deleteAllNews()

    fun observeNewsByCategory(category: String): Flow<List<News>>

    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    fun fetchNewsByCategory(category: String, from: String): Flow<List<News>>

    fun fetchTrendingNews(): Flow<List<SourcedNews>>

    suspend fun searchNews(category: String, from: String): Flow<List<News>>
}