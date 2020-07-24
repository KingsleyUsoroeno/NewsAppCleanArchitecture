package com.techkingsley.newsappcleanarchitecture.business.data.cache.repository

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.interactors.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface INewsAppRepository {

    suspend fun refreshTechNews(category: String)

    suspend fun refreshTrendingNews(category: String)

    suspend fun refreshPoliticalNews(category: String)

    suspend fun refreshMovieNews(category: String)

    fun getSearchHistory(): Flow<List<SearchHistory>>

    fun observeAllNews(category: String): Flow<List<News>>

    suspend fun addSearchHistory(searchHistory: SearchHistory)

    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    suspend fun searchNews(query: String): Flow<ResultWrapper<List<NewsNetworkEntity>>>
}