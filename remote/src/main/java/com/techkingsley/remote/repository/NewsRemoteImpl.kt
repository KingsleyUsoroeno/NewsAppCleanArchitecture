package com.techkingsley.remote.repository

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.data.repository.news.NewsRemote
import com.techkingsley.data.state.Result
import com.techkingsley.remote.mapper.news.NewsEntityMapper
import com.techkingsley.remote.mapper.trending.TrendingNewsMapper
import com.techkingsley.remote.source.news.NewsDataSource
import javax.inject.Inject

class NewsRemoteImpl @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val newsEntityMapper: NewsEntityMapper = NewsEntityMapper(),
    private val trendingNewsMapper: TrendingNewsMapper = TrendingNewsMapper()
) : NewsRemote {

    override suspend fun fetchTechNews(category: String, from: String, sortBy: String, apiKey: String): Result<List<NewsEntity>> {
        return newsDataSource.fetchTechNews(category, from, sortBy, apiKey) { newsResponse ->
            newsEntityMapper.mapFromRemote(category, newsResponse!!)
        }
    }

    override suspend fun fetchTrendingNews(category: String, apiKey: String): Result<List<SourceNewsEntity>> {
        return newsDataSource.fetchTrendingNews(apiKey) { newsResponse ->
            trendingNewsMapper.mapFromRemote(newsResponse!!)
        }
    }

    override suspend fun fetchPoliticalNews(category: String, from: String, sortBy: String, apiKey: String): Result<List<NewsEntity>> {
        return newsDataSource.fetchPoliticalNews(category, from, sortBy, apiKey) { newsResponse ->
            newsEntityMapper.mapFromRemote(category, newsResponse!!)
        }
    }

    override suspend fun fetchMovieNews(category: String, from: String, sortBy: String, apiKey: String): Result<List<NewsEntity>> {
        return newsDataSource.fetchMovieNews(category, from, sortBy, apiKey) { newsResponse ->
            newsEntityMapper.mapFromRemote(category, newsResponse!!)
        }
    }

    override suspend fun searchNews(category: String, from: String, sortBy: String, apiKey: String): Result<List<NewsEntity>> {
        return newsDataSource.fetchMovieNews(category, from, sortBy, apiKey) { newsResponse ->
            newsEntityMapper.mapFromRemote(category, newsResponse!!)
        }
    }
}