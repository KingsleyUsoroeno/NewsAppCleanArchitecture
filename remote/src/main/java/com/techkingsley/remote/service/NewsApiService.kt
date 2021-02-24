package com.techkingsley.remote.service

import com.techkingsley.remote.model.NewsResponse
import com.techkingsley.remote.model.SourceNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/sources")
    suspend fun getTrendingNews(@Query("apiKey") apiKey: String): Response<SourceNewsResponse>

    @GET("v2/top-headlines")
    suspend fun getTrendingNewsDetail(
        @Query("country") country: String
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getNewsByCategory(
        @Query("q") category: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}