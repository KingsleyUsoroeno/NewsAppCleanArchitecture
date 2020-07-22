package com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.service

import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsBaseResponse
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.SourceNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/sources")
    suspend fun getTrendingNews(@Query("apiKey") apiKey: String): Response<SourceNewsResponse>

    @GET("v2/top-headlines")
    suspend fun getTrendingNewsDetail(
        @Query("country") country: String
    ): Response<NewsBaseResponse>

    @GET("v2/everything")
    suspend fun getNewsByCategory(
        @Query("q") category: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsBaseResponse>

    suspend fun searchNews(query: String)
}