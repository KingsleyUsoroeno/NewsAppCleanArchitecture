package com.techkingsley.newsappcleanarchitecture.business.data.cache.repository

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews
import kotlinx.coroutines.flow.Flow

interface INewsAppRepository {

    suspend fun refreshTechNews(category: String)

    suspend fun refreshTrendingNews(category: String)

    suspend fun refreshPoliticalNews(category: String)

    suspend fun refreshMovieNews(category: String)

    fun getTechNews(): Flow<List<TechnologyNews>>

    fun getPoliticalNews(): Flow<List<PoliticalNews>>

    fun getMovieNews(): Flow<List<Movies>>

    fun getTrendingNews(): Flow<List<TrendingNews>>
}