package com.techkingsley.domain.repositories

import com.techkingsley.domain.entities.News
import com.techkingsley.domain.entities.SearchHistory
import com.techkingsley.domain.entities.SourcedNews
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

    suspend fun fetchTechNews(category: String, from: String, sortBy: String, apiKey: String)

    suspend fun fetchTrendingNews(category: String, apiKey: String): List<SourcedNews>

    suspend fun fetchPoliticalNews(category: String, from: String, sortBy: String, apiKey: String)

    suspend fun fetchMovieNews(category: String, from: String, sortBy: String, apiKey: String)

    suspend fun searchNews(category: String, from: String, sortBy: String, apiKey: String): List<News>
}