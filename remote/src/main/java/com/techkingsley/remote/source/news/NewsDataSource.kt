package com.techkingsley.remote.source.news

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.data.state.Result
import com.techkingsley.remote.data.model.NewsResponse
import com.techkingsley.remote.data.model.SourceNewsResponse

interface NewsDataSource {

    suspend fun fetchTechNews(category: String, from: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>): Result<List<NewsEntity>>

    suspend fun fetchTrendingNews(mapResponse: (response: SourceNewsResponse?) -> List<SourceNewsEntity>): Result<List<SourceNewsEntity>>

    suspend fun fetchPoliticalNews(category: String, from: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>): Result<List<NewsEntity>>

    suspend fun fetchMovieNews(category: String, from: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>): Result<List<NewsEntity>>

    suspend fun searchNews(category: String, from: String, mapResponse: (response: NewsResponse?) -> List<NewsEntity>): Result<List<NewsEntity>>
}