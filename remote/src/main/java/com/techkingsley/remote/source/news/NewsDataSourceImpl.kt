package com.techkingsley.remote.source.news

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.data.state.Result
import com.techkingsley.remote.model.NewsResponse
import com.techkingsley.remote.model.SourceNewsResponse
import com.techkingsley.remote.service.NewsApiService
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsDataSource {

    override suspend fun fetchTechNews(
        category: String, from: String, sortBy: String, apiKey: String,
        mapResponse: (response: NewsResponse?) -> List<NewsEntity>
    ): Result<List<NewsEntity>> {
        return fetchNews(category, from, sortBy, apiKey, mapResponse)
    }

    override suspend fun fetchTrendingNews(apiKey: String, mapResponse: (response: SourceNewsResponse?) -> List<SourceNewsEntity>): Result<List<SourceNewsEntity>> {
        val response = newsApiService.getTrendingNews(apiKey)
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(mapResponse(response.body()))
        } else {
            Result.Error(Exception(response.errorBody()?.string()))
        }
    }

    override suspend fun fetchPoliticalNews(
        category: String,
        from: String,
        sortBy: String,
        apiKey: String,
        mapResponse: (response: NewsResponse?) -> List<NewsEntity>
    ): Result<List<NewsEntity>> {
        return fetchNews(category, from, sortBy, apiKey, mapResponse)
    }

    override suspend fun fetchMovieNews(
        category: String,
        from: String,
        sortBy: String,
        apiKey: String,
        mapResponse: (response: NewsResponse?) -> List<NewsEntity>
    ): Result<List<NewsEntity>> {
        return fetchNews(category, from, sortBy, apiKey, mapResponse)
    }

    override suspend fun searchNews(category: String, from: String, sortBy: String, apiKey: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>): Result<List<NewsEntity>> {
        return fetchNews(category, from, sortBy, apiKey, mapResponse)
    }


    private suspend fun fetchNews(category: String, from: String, sortBy: String, apiKey: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>): Result<List<NewsEntity>> {
        return try {
            val response = newsApiService.getNewsByCategory(category, from, sortBy, apiKey)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(mapResponse(response.body()))
            } else {
                Result.Error(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}