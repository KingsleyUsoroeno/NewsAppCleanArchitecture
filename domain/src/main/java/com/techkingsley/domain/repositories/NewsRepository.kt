package com.techkingsley.domain.repositories

import com.techkingsley.domain.models.NewsResult
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.models.news.SourcedNews
import com.techkingsley.domain.models.searchhistory.SearchHistory
import kotlinx.coroutines.flow.Flow

/** An abstract definition of a bookRepository used when communicating with the data layer*/
interface NewsRepository {

    fun observeSearchHistory(): Flow<List<SearchHistory>>

    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    suspend fun insertNews(news: News)

    suspend fun insertAllNews(category: String, news: List<News>)

    suspend fun deleteNews(news: News)

    suspend fun deleteAllNews()

    fun getNewsByCategory(category: String, from: String): Flow<NewsResult>

    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    suspend fun fetchNewsByCategory(category: String, from: String): List<News>

    suspend fun fetchTrendingNews(): List<SourcedNews>

    suspend fun searchNews(category: String, from: String): Flow<List<News>>

    fun observeNews(category: String): Flow<List<News>>
}