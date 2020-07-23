package com.techkingsley.newsappcleanarchitecture.framework.datasource.network

import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsBaseResponse
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.SourceNewsResponse
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.service.NewsApiService
import retrofit2.Response

class RemoteDataSourceImpl(private val newsApiService: NewsApiService) : RemoteDataSource {

    override suspend fun getTrendingNews(apiKey: String): Response<SourceNewsResponse> {
        return newsApiService.getTrendingNews(apiKey)
    }

    override suspend fun getTrendingNewsDetail(country: String): Response<NewsBaseResponse> {
        return newsApiService.getTrendingNewsDetail(country)
    }

    override suspend fun getNewsByCategory(category: String, from: String, sortBy: String, apiKey: String): Response<NewsBaseResponse> {
        return newsApiService.getNewsByCategory(category, from, sortBy, apiKey)
    }

    override suspend fun searchNews(query: String) {
        //newsApiService.searchNews(query)
    }

}