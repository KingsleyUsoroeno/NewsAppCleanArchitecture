package com.techkingsley.newsappcleanarchitecture.framework.datasource.cache

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews
import kotlinx.coroutines.flow.Flow

interface LocalCacheDataSource {

    suspend fun insertTrendingNews(news: TrendingNews)

    suspend fun insertAllTechnologyNews(techNews: List<TechnologyNews>)

    suspend fun insertAllPoliticalNews(politicalNews: List<PoliticalNews>)

    suspend fun insertAllTrendingNews(trendingNews: List<TrendingNews>)

    suspend fun insertPoliticalNews(news: PoliticalNews)

    suspend fun insertMovieNews(news: Movies)

    suspend fun insertAllMovieNews(movies: List<Movies>)

    suspend fun insertTechNews(techNews: TechnologyNews)

    fun observeTechNews(): Flow<List<TechnologyNews>>

    fun getTrendingNews(): List<TrendingNews>

    fun observePoliticalNews(): Flow<List<PoliticalNews>>

    fun observeMovieNews(): Flow<List<Movies>>

    fun observeTrendingNews(): Flow<List<TrendingNews>>

}