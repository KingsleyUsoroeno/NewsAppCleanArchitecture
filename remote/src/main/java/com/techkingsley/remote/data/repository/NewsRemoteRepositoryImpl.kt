package com.techkingsley.remote.data.repository

import com.google.gson.Gson
import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.data.state.Result
import com.techkingsley.remote.data.response.ServerErrorResponse
import com.techkingsley.remote.exceptions.ServerException
import com.techkingsley.remote.mapper.news.NewsEntityMapper
import com.techkingsley.remote.mapper.trending.TrendingNewsMapper
import com.techkingsley.remote.service.NewsApiService
import com.techkingsley.remote.utils.RemoteConstants
import com.techkingsley.remote.utils.ResultWrapper
import com.techkingsley.remote.utils.safeApiResult
import okhttp3.ResponseBody
import javax.inject.Inject

class NewsRemoteRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsEntityMapper: NewsEntityMapper = NewsEntityMapper(),
    private val trendingNewsMapper: TrendingNewsMapper = TrendingNewsMapper()
) : NewsRemoteRepository {

    private val apiKey: String = RemoteConstants.API_KEY

    private val publishedAt: String = RemoteConstants.PUBLISHED_AT

    override suspend fun fetchTechNews(category: String, from: String): Result<List<NewsEntity>> {
        val result = safeApiResult(genericError = { error -> getServerError(error) },
            call = { newsApiService.getNewsByCategory(category, from, publishedAt, apiKey) })

        return when (result) {
            is ResultWrapper.GenericError -> {
                val errorMessage = result.errorResponse as ServerErrorResponse
                Result.Error(ServerException(errorMessage.message))
            }
            is ResultWrapper.NetworkError -> Result.Error(result.exception)
            is ResultWrapper.Success -> Result.Success(newsEntityMapper.mapFromRemote(category, result.value))
        }
    }

    override suspend fun fetchTrendingNews(): Result<List<SourceNewsEntity>> {
        val result = safeApiResult(genericError = { error -> getServerError(error) },
            call = { newsApiService.getTrendingNews(apiKey) })

        return when (result) {
            is ResultWrapper.Success -> Result.Success(trendingNewsMapper.mapFromRemote(result.value))
            is ResultWrapper.GenericError -> {
                val errorMessage = result.errorResponse as ServerErrorResponse
                Result.Error(ServerException(errorMessage.message))
            }
            is ResultWrapper.NetworkError -> Result.Error(result.exception)
        }
    }

    override suspend fun fetchPoliticalNews(category: String, from: String): Result<List<NewsEntity>> {
        val result = safeApiResult(genericError = { error -> getServerError(error) },
            call = { newsApiService.getNewsByCategory(category, from, publishedAt, apiKey) })

        return when (result) {
            is ResultWrapper.GenericError -> {
                val errorMessage = result.errorResponse as ServerErrorResponse
                Result.Error(ServerException(errorMessage.message))
            }
            is ResultWrapper.NetworkError -> Result.Error(result.exception)
            is ResultWrapper.Success -> Result.Success(newsEntityMapper.mapFromRemote(category, result.value))
        }
    }

    override suspend fun fetchMovieNews(category: String, from: String): Result<List<NewsEntity>> {
        val result = safeApiResult(genericError = { error -> getServerError(error) },
            call = { newsApiService.getNewsByCategory(category, from, publishedAt, apiKey) })

        return when (result) {
            is ResultWrapper.GenericError -> {
                val errorMessage = result.errorResponse as ServerErrorResponse
                Result.Error(ServerException(errorMessage.message))
            }
            is ResultWrapper.NetworkError -> Result.Error(result.exception)
            is ResultWrapper.Success -> Result.Success(newsEntityMapper.mapFromRemote(category, result.value))
        }
    }

    override suspend fun searchNews(category: String, from: String): Result<List<NewsEntity>> {
        val result = safeApiResult(genericError = { error -> getServerError(error) },
            call = { newsApiService.getNewsByCategory(category, from, publishedAt, apiKey) })

        return when (result) {
            is ResultWrapper.GenericError -> {
                val errorMessage = result.errorResponse as ServerErrorResponse
                Result.Error(ServerException(errorMessage.message))
            }
            is ResultWrapper.NetworkError -> Result.Error(result.exception)
            is ResultWrapper.Success -> Result.Success(newsEntityMapper.mapFromRemote(category, result.value))
        }
    }

    private fun getServerError(response: ResponseBody?): ServerErrorResponse {
        return Gson().fromJson(response?.string(), ServerErrorResponse::class.java)
    }
}