package com.techkingsley.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.techkingsley.cache.model.CachedNews
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao : BaseDao<CachedNews> {

    @Query("SELECT * FROM CachedNews")
    fun observeMovieNews(): Flow<List<CachedNews>>

    @Query("SELECT * FROM CachedNews WHERE newsCategory = :category")
    fun observeNewsByCategory(category: String): Flow<List<CachedNews>>

    @Query("DELETE FROM CachedNews")
    suspend fun deleteAllNews()

    @Transaction
    suspend fun updateNews(movies: List<CachedNews>) {
        deleteAllNews()
        insertAll(movies)
    }
}