package com.techkingsley.newsappcleanarchitecture.business.data.cache.repository

import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.NewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.*
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.interactors.FetchNews
import com.techkingsley.newsappcleanarchitecture.business.interactors.ResultWrapper
import com.techkingsley.newsappcleanarchitecture.business.interactors.doIfSuccess
import com.techkingsley.newsappcleanarchitecture.framework.Secret.API_KEY
import com.techkingsley.newsappcleanarchitecture.framework.datasource.cache.LocalCacheDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class NewsAppRepository constructor(
    private val fetchNews: FetchNews,
    private val localCacheDataSource: LocalCacheDataSource,
    private val newsMapper: NewsMapper = NewsMapper()
) : INewsAppRepository {

    companion object {
        private const val SORT_BY = "publishedAt"
    }

    private fun getTodayDateAsString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private val from = getTodayDateAsString()

    override suspend fun refreshTechNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchTechnologicalNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                val techNews = newsMapper.mapFromEntityList(category, news.articles)
                localCacheDataSource.insertAllNews(techNews)
            }
        }
    }

    override suspend fun refreshTrendingNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchTrendingNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                val techNews = newsMapper.mapFromEntityList(category, news.articles)
                localCacheDataSource.insertAllNews(techNews)
            }
        }
    }

    override suspend fun refreshPoliticalNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchPoliticalNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                val techNews = newsMapper.mapFromEntityList(category, news.articles)
                localCacheDataSource.insertAllNews(techNews)
            }
        }
    }

    override suspend fun refreshMovieNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchPoliticalNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                val techNews = newsMapper.mapFromEntityList(category, news.articles)
                localCacheDataSource.insertAllNews(techNews)
            }
        }
    }

    override fun getSearchHistory(): Flow<List<SearchHistory>> {
        return localCacheDataSource.observeSearchHistory()
    }

    override fun observeAllNews(category: String): Flow<List<News>> {
        return localCacheDataSource.observeAllNewsByCategory(category)
    }

    override suspend fun addSearchHistory(searchHistory: SearchHistory) {
        localCacheDataSource.addSearchHistory(searchHistory)
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        localCacheDataSource.deleteSearchHistory(searchHistory)
    }

    /*Map these out to a different entity object*/
    override suspend fun searchNews(query: String): Flow<ResultWrapper<List<NewsNetworkEntity>>> {
        return fetchNews.searchNews(query, from, SORT_BY, API_KEY)
    }
}