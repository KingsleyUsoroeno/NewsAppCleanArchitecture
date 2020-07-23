package com.techkingsley.newsappcleanarchitecture.business.data.cache.repository

import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.MovieNewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.PoliticalNewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.TechNewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.TrendingNewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.*
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.interactors.FetchNews
import com.techkingsley.newsappcleanarchitecture.business.interactors.ResultWrapper
import com.techkingsley.newsappcleanarchitecture.business.interactors.doIfSuccess
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
    private val techNewsMapper: TechNewsMapper = TechNewsMapper(),
    private val politicalNewsMapper: PoliticalNewsMapper = PoliticalNewsMapper(),
    private val trendingNewsMapper: TrendingNewsMapper = TrendingNewsMapper(),
    private val movieNewsMapper: MovieNewsMapper = MovieNewsMapper()
) : INewsAppRepository {

    companion object {
        private const val SORT_BY = "publishedAt"
        private const val API_KEY = "ca4ae9f450a44a39bd7b77f9a8745450"
    }

    private fun getTodayDateAsString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private val from = getTodayDateAsString()

    override suspend fun refreshTechNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchTechnologicalNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                localCacheDataSource.updateTechNews(techNewsMapper.mapFromEntityList(news.articles))
            }
        }
    }

    override suspend fun refreshTrendingNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchTrendingNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                localCacheDataSource.updateTrendingNews(trendingNewsMapper.mapFromEntityList(news.articles))
            }
        }
    }

    override suspend fun refreshPoliticalNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchPoliticalNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                localCacheDataSource.updatePoliticalNews(politicalNewsMapper.mapFromEntityList(news.articles))
            }
        }
    }

    override suspend fun refreshMovieNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchPoliticalNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                localCacheDataSource.updateMovieNews(movieNewsMapper.mapFromEntityList(news.articles))
            }
        }
    }

    override fun getTechNews(): Flow<List<TechnologyNews>> {
        return localCacheDataSource.observeTechNews()
    }

    override fun getPoliticalNews(): Flow<List<PoliticalNews>> {
        return localCacheDataSource.observePoliticalNews()
    }

    override fun getMovieNews(): Flow<List<Movies>> {
        return localCacheDataSource.observeMovieNews()
    }

    override fun getTrendingNews(): Flow<List<TrendingNews>> {
        return localCacheDataSource.observeTrendingNews()
    }

    override fun getSearchHistory(): Flow<List<SearchHistory>> {
        return localCacheDataSource.getSearchHistory()
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