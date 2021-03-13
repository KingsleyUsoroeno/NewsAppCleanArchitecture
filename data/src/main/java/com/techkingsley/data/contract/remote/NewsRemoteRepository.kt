package com.techkingsley.data.contract.remote

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.data.state.Result

interface NewsRemoteRepository {

    suspend fun fetchTechNews(category: String, from: String): Result<List<NewsEntity>>

    suspend fun fetchTrendingNews(): Result<List<SourceNewsEntity>>

    suspend fun fetchPoliticalNews(category: String, from: String): Result<List<NewsEntity>>

    suspend fun fetchMovieNews(category: String, from: String): Result<List<NewsEntity>>

    suspend fun searchNews(category: String, from: String): Result<List<NewsEntity>>
}