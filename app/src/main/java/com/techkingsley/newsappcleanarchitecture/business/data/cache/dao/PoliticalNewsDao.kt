package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import kotlinx.coroutines.flow.Flow

@Dao
interface PoliticalNewsDao : BaseDao<PoliticalNews> {

    @Query("SELECT * FROM PoliticalNews")
    fun observePoliticalNews(): Flow<List<PoliticalNews>>

    @Query("DELETE FROM PoliticalNews")
    suspend fun clearPoliticalNewsTable()

    @Transaction
    suspend fun updatePoliticalNews(politicalNews: List<PoliticalNews>) {
        clearPoliticalNewsTable()
        insertAll(politicalNews)
    }
}