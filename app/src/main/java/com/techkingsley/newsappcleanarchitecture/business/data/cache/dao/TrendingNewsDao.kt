package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingNewsDao : BaseDao<TrendingNews> {

    @Query("SELECT * FROM trendingnews")
    fun observeTrendingNews(): Flow<List<TrendingNews>>

    @Query("SELECT * FROM trendingnews")
    fun getTrendingNews(): List<TrendingNews>
}