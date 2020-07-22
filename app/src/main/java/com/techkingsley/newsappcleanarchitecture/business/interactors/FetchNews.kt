package com.techkingsley.newsappcleanarchitecture.business.interactors

import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsBaseResponse
import kotlinx.coroutines.flow.Flow

interface FetchNews {

    fun fetchTechnologicalNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>>

    fun fetchTrendingNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>>

    fun fetchPoliticalNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>>

    fun fetchMovieNews(category: String, from: String, sortBy: String, apiKey: String): Flow<ResultWrapper<NewsBaseResponse>>
}