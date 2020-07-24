package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao : BaseDao<News> {

    @Query("SELECT * FROM News")
    fun observeMovieNews(): Flow<List<News>>

    @Query("SELECT * FROM News WHERE newsCategory = :category")
    fun observeNewsByCategory(category: String): Flow<List<News>>

    @Query("DELETE FROM News")
    suspend fun deleteAllNews()

    @Transaction
    suspend fun updateNews(movies: List<News>) {
        deleteAllNews()
        insertAll(movies)
    }
}