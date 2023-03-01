package com.techkingsley.domain.repositories

import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.models.news.SourcedNews
import com.techkingsley.domain.models.searchhistory.SearchHistory
import kotlinx.coroutines.flow.Flow

/** An abstract definition of a [NewsRepository] used when communicating with the data layer*/
interface NewsRepository {

    fun observeSearchHistory(): Flow<List<SearchHistory>>

    fun observeBookmarkedNews(): Flow<List<News>>

    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    suspend fun insertAllNews(news: List<News>)

    suspend fun deleteNews(news: News)

    suspend fun deleteAllNews()

    fun observeNewsByCategory(category: String, from: String): Flow<List<News>>

    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    suspend fun fetchTrendingNews(): List<SourcedNews>

    fun searchNews(category: String, from: String): Flow<List<News>>

    suspend fun getNewsByCategory(category: String): List<News>

    suspend fun saveOrRemoveNewsFromBookmarks(news: News)
}