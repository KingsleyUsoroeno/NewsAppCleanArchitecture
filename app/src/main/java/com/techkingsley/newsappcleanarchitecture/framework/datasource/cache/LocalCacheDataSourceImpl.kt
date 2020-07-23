package com.techkingsley.newsappcleanarchitecture.framework.datasource.cache

import com.techkingsley.newsappcleanarchitecture.business.data.cache.db.NewsDatabase
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

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

    override suspend fun updateMovieNews(movies: List<Movies>) {
        db.movieDao().updateMovies(movies)
    }

    override suspend fun updatePoliticalNews(politicalNews: List<PoliticalNews>) {
        db.politicalNewsDao().updatePoliticalNews(politicalNews)
    }

    override suspend fun updateTechNews(techNews: List<TechnologyNews>) {
        db.techNewsDao().updateTechNews(techNews)
    }

    override suspend fun updateTrendingNews(trendingNews: List<TrendingNews>) {
        db.trendingNewsDao().updateTrendingNews(trendingNews)
    }

    override fun getSearchHistory(): Flow<List<SearchHistory>> {
        return db.searchHistoryDao().observeSearchHistory()
    }

    override suspend fun addSearchHistory(searchHistory: SearchHistory) {
        withContext(Dispatchers.IO) {
            db.searchHistoryDao().insert(searchHistory)
        }
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) = withContext(Dispatchers.IO) {
        db.searchHistoryDao().delete(searchHistory)
    }

}