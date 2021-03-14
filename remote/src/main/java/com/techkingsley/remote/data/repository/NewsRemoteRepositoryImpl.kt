package com.techkingsley.remote.data.repository

import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.remote.mapper.news.NewsEntityMapper
import com.techkingsley.remote.mapper.trending.TrendingNewsMapper
import com.techkingsley.remote.service.NewsApiService
import com.techkingsley.remote.utils.RemoteConstants
import javax.inject.Inject

class NewsRemoteRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsEntityMapper: NewsEntityMapper,
    private val trendingNewsMapper: TrendingNewsMapper
) : NewsRemoteRepository {

    private val apiKey: String = RemoteConstants.API_KEY

    private val publishedAt: String = RemoteConstants.PUBLISHED_AT

    override suspend fun fetchTrendingNews(): List<SourceNewsEntity> {
        val trendingNews = newsApiService.getTrendingNews(apiKey)
        return trendingNewsMapper.mapFromRemote(trendingNews)
    }

    override suspend fun fetchNewsByCategory(category: String, from: String): List<NewsEntity> {
        val news = newsApiService.getNewsByCategory(category, from, publishedAt, apiKey)
        return newsEntityMapper.mapFromRemote(category, news)
    }
}