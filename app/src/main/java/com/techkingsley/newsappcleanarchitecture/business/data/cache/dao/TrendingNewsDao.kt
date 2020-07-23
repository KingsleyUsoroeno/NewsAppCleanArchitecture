package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingNewsDao : BaseDao<TrendingNews> {

    @Query("SELECT * FROM trendingnews")
    fun observeTrendingNews(): Flow<List<TrendingNews>>

    @Query("SELECT * FROM trendingnews")
    fun getTrendingNews(): List<TrendingNews>

    @Query("DELETE FROM TrendingNews")
    suspend fun clearTrendingNewsTable()

    @Transaction
    suspend fun updateTrendingNews(trendingNews: List<TrendingNews>) {
        clearTrendingNewsTable()
        insertAll(trendingNews)
    }
}