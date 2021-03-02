package com.techkingsley.remote.source.news

import com.google.gson.Gson
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.data.state.Result
import com.techkingsley.remote.data.model.NewsResponse
import com.techkingsley.remote.data.model.SourceNewsResponse
import com.techkingsley.remote.data.response.ServerErrorResponse
import com.techkingsley.remote.service.NewsApiService
import com.techkingsley.remote.utils.RemoteConstants
import okhttp3.ResponseBody
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsDataSource {

    private val apiKey: String = RemoteConstants.API_KEY
    private val publishedAt: String = RemoteConstants.PUBLISHED_AT


    override suspend fun fetchTechNews(
        category: String, from: String,
        mapResponse: (response: NewsResponse?) -> List<NewsEntity>
    ): Result<List<NewsEntity>> {
        return fetchNews(category, from, publishedAt, apiKey, mapResponse)
    }

    override suspend fun fetchTrendingNews(mapResponse: (response: SourceNewsResponse?) -> List<SourceNewsEntity>): Result<List<SourceNewsEntity>> {
        val response = newsApiService.getTrendingNews(apiKey)
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(mapResponse(response.body()))
        } else {
            Result.Error(Exception(response.errorBody()?.string()))
        }
    }

    override suspend fun fetchPoliticalNews(
        category: String, from: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>
    ): Result<List<NewsEntity>> {
        return fetchNews(category, from, publishedAt, apiKey, mapResponse)
    }

    override suspend fun fetchMovieNews(
        category: String,
        from: String,
        mapResponse: (response: NewsResponse?) -> List<NewsEntity>
    ): Result<List<NewsEntity>> {
        return fetchNews(category, from, publishedAt, apiKey, mapResponse)
    }

    override suspend fun searchNews(category: String, from: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>): Result<List<NewsEntity>> {
        return fetchNews(category, from, publishedAt, apiKey, mapResponse)
    }


    private suspend fun fetchNews(category: String, from: String, sortBy: String, apiKey: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>): Result<List<NewsEntity>> {
        return try {
            val response = newsApiService.getNewsByCategory(category, from, sortBy, apiKey)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(mapResponse(response.body()))
            } else {
                return Result.Error(Exception(getServerError(response.errorBody()).message))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun getServerError(response: ResponseBody?): ServerErrorResponse {
        return Gson().fromJson(response?.string(), ServerErrorResponse::class.java)
    }

}