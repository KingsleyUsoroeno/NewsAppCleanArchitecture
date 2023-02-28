package com.techkingsley.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.techkingsley.cache.dao.base.BaseDao
import com.techkingsley.cache.entities.CacheNews
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao : BaseDao<CacheNews> {

    @get:Query("SELECT * FROM CacheNews")
    val observeNews: Flow<List<CacheNews>>

    @Query("SELECT * FROM CacheNews WHERE newsCategory = :category LIMIT 10")
    suspend fun getNewsByCategory(category: String): List<CacheNews>

    @Query("SELECT * FROM CacheNews WHERE newsCategory = :category LIMIT 10")
    fun observeNewsByCategory(category: String): Flow<List<CacheNews>>

    @Query("DELETE FROM CacheNews")
    suspend fun deleteAllNews()

    @Query("select count(*) from CacheNews")
    suspend fun getTotalNewsCount(): Int

    @Transaction
    suspend fun updateNews(news: List<CacheNews>) {
        // so basically i would love to keep the news that are still bookmarked safe in the db
        // while deleting the rest stall data and adding the recently fetched ones from the server
        deleteNonBookmarkedNews()
        insertAll(news + getBookmarkedNews())
    }

    @Query("update CacheNews set isBookmarked = :isBookmarked where id = :id")
    suspend fun toggleNewsBookmarkStatus(isBookmarked: Boolean, id: Long)

    @Query("DELETE FROM CacheNews WHERE id = :id")
    suspend fun deleteNewsById(id: Long)

    @Query("select * from CacheNews where isBookmarked = :isBookmarked")
    fun observeBookmarkedNews(isBookmarked: Boolean = true): Flow<List<CacheNews>>

    @Query("select * from CacheNews where isBookmarked = :isBookmarked")
    suspend fun getBookmarkedNews(isBookmarked: Boolean = true): List<CacheNews>

    @Query("delete from CacheNews where isBookmarked = :isBookmarked")
    suspend fun deleteNonBookmarkedNews(isBookmarked: Boolean = false)
}