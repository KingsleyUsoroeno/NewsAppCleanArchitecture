package com.techkingsley.newsappcleanarchitecture.framework.datasource.network

import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsBaseResponse
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.SourceNewsResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getTrendingNews(apiKey: String): Response<SourceNewsResponse>

    suspend fun getTrendingNewsDetail(country: String): Response<NewsBaseResponse>

    suspend fun getNewsByCategory(category: String, from: String, sortBy: String, apiKey: String): Response<NewsBaseResponse>

    suspend fun searchNews(query: String)
}