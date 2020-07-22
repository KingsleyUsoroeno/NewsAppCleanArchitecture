package com.techkingsley.newsappcleanarchitecture.framework.datasource.cache

import com.techkingsley.newsappcleanarchitecture.business.data.cache.db.NewsDatabase
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews
import kotlinx.coroutines.flow.Flow

class LocalCacheDataSourceImpl(private val db: NewsDatabase) : LocalCacheDataSource {

    override suspend fun insertTrendingNews(news: TrendingNews) {
        db.trendingNewsDao().insert(news)
    }

    override suspend fun insertAllTechnologyNews(techNews: List<TechnologyNews>) {
        db.techNewsDao().insertAll(techNews)
    }

    override suspend fun insertAllPoliticalNews(politicalNews: List<PoliticalNews>) {
        db.politicalNewsDao().insertAll(politicalNews)
    }

    override suspend fun insertAllTrendingNews(trendingNews: List<TrendingNews>) {
        db.trendingNewsDao().insertAll(trendingNews)
    }

    override suspend fun insertPoliticalNews(news: PoliticalNews) {
        db.politicalNewsDao().insert(news)
    }

    override suspend fun insertMovieNews(news: Movies) {
        db.movieDao().insert(news)
    }

    override suspend fun insertAllMovieNews(movies: List<Movies>) {
        db.movieDao().insertAll(movies)
    }

    override suspend fun insertTechNews(techNews: TechnologyNews) {
        db.techNewsDao().insert(techNews)
    }

    override fun observeTechNews(): Flow<List<TechnologyNews>> {
        return db.techNewsDao().observeTechnologyNews()
    }

    override fun getTrendingNews(): List<TrendingNews> {
        return db.trendingNewsDao().getTrendingNews()
    }

    override fun observePoliticalNews(): Flow<List<PoliticalNews>> {
        return db.politicalNewsDao().observePoliticalNews()
    }

    override fun observeMovieNews(): Flow<List<Movies>> {
        return db.movieDao().observeMovieNews()
    }

    override fun observeTrendingNews(): Flow<List<TrendingNews>> {
        return db.trendingNewsDao().observeTrendingNews()
    }

}