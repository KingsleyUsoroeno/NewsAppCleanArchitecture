package com.techkingsley.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.techkingsley.cache.models.CachedNews
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao : BaseDao<CachedNews> {

    @get:Query("SELECT * FROM CachedNews")
    val observeNews: Flow<List<CachedNews>>

    @Query("SELECT * FROM CachedNews WHERE newsCategory = :category")
    suspend fun getNewsByCategory(category: String): List<CachedNews>

    @Query("SELECT * FROM CachedNews WHERE newsCategory = :category")
    fun observeNewsByCategory(category: String): Flow<List<CachedNews>>

    @Query("DELETE FROM CachedNews")
    suspend fun deleteAllNews()

    @Transaction
    suspend fun updateNews(movies: List<CachedNews>) {
        deleteAllNews()
        insertAll(movies)
    }

    @Query("SELECT COUNT(*) FROM CachedNews WHERE newsCategory =:newsCategory ")
    suspend fun getCount(newsCategory: String): Int?
}