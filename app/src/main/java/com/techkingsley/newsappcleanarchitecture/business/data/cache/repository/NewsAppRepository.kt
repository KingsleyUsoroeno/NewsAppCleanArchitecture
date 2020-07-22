package com.techkingsley.newsappcleanarchitecture.business.data.cache.repository

import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.MovieNewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.PoliticalNewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.TechNewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers.TrendingNewsMapper
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews
import com.techkingsley.newsappcleanarchitecture.business.interactors.FetchNews
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
                localCacheDataSource.insertAllTechnologyNews(techNewsMapper.mapFromEntityList(news.articles))
            }
        }
    }

    override suspend fun refreshTrendingNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchTrendingNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                localCacheDataSource.insertAllTrendingNews(trendingNewsMapper.mapFromEntityList(news.articles))
            }
        }
    }

    override suspend fun refreshPoliticalNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchPoliticalNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                localCacheDataSource.insertAllPoliticalNews(politicalNewsMapper.mapFromEntityList(news.articles))
            }
        }
    }

    override suspend fun refreshMovieNews(category: String) = withContext(Dispatchers.IO) {
        fetchNews.fetchPoliticalNews(category, from, SORT_BY, API_KEY).collect {
            it.doIfSuccess { news ->
                localCacheDataSource.insertAllMovieNews(movieNewsMapper.mapFromEntityList(news.articles))
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
}