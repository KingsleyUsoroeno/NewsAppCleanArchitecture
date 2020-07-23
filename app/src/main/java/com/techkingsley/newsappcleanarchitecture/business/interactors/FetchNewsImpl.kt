package com.techkingsley.newsappcleanarchitecture.business.interactors

import android.util.Log
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsBaseResponse
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.framework.datasource.network.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNewsImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : FetchNews {

    companion object {
        private const val TAG = "FetchNewsDebug"
    }

    override fun fetchTechnologicalNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>> {
        return fetchNewsByCategory(category, from, sortBy, apiKey)
    }

    override fun fetchTrendingNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>> {
        return fetchNewsByCategory(category, from, sortBy, apiKey)
    }

    override fun fetchPoliticalNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>> {
        return fetchNewsByCategory(category, from, sortBy, apiKey)
    }

    override fun fetchMovieNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>> {
        return fetchNewsByCategory(category, from, sortBy, apiKey)
    }

    override fun searchNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<List<NewsNetworkEntity>>> = flow {
        emit(ResultWrapper.Loading)
        val networkResponse = safeApiResult { remoteDataSource.getNewsByCategory(category, from, sortBy, apiKey) }
        networkResponse.doIfSuccess { response ->
            Log.i(TAG, "$category News is $response")
            emit(ResultWrapper.Success(response.articles))
        }
        networkResponse.doIfFailure { emit(ResultWrapper.GenericError(errorResponse = it)) }
        networkResponse.doIfNetworkException { emit(ResultWrapper.NetworkError(exception = it)) }
    }

    private fun fetchNewsByCategory(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>> = flow {
        emit(ResultWrapper.Loading)
        val networkResponse = safeApiResult { remoteDataSource.getNewsByCategory(category, from, sortBy, apiKey) }
        networkResponse.doIfSuccess { response ->
            Log.i(TAG, "$category News is $response")
            emit(ResultWrapper.Success(response))
        }
        networkResponse.doIfFailure { emit(ResultWrapper.GenericError(errorResponse = it)) }
        networkResponse.doIfNetworkException { emit(ResultWrapper.NetworkError(exception = it)) }
    }
}