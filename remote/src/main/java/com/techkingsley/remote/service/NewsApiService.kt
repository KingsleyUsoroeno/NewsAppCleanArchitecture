package com.techkingsley.remote.service

import com.techkingsley.remote.data.model.NewsResponse
import com.techkingsley.remote.data.model.SourceNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/sources")
    suspend fun getTrendingNews(@Query("apiKey") apiKey: String): SourceNewsResponse

    @GET("v2/everything")
    suspend fun getNewsByCategory(
        @Query("q") category: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}