package com.techkingsley.data.repository.news

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.data.state.Result

interface NewsRemote {

    suspend fun fetchTechNews(category: String, from: String, sortBy: String, apiKey: String): Result<List<NewsEntity>>

    suspend fun fetchTrendingNews(category: String, apiKey: String): Result<List<SourceNewsEntity>>

    suspend fun fetchPoliticalNews(category: String, from: String, sortBy: String, apiKey: String): Result<List<NewsEntity>>

    suspend fun fetchMovieNews(category: String, from: String, sortBy: String, apiKey: String): Result<List<NewsEntity>>

    suspend fun searchNews(category: String, from: String, sortBy: String, apiKey: String): Result<List<NewsEntity>>
}