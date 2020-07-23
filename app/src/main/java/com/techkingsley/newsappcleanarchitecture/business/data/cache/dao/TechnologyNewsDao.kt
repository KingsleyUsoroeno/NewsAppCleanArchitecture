package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import kotlinx.coroutines.flow.Flow

@Dao
interface TechnologyNewsDao : BaseDao<TechnologyNews> {

    @Query("SELECT * FROM technologynews")
    fun observeTechnologyNews(): Flow<List<TechnologyNews>>

    @Query("DELETE FROM TechnologyNews")
    suspend fun clearTechNewsTable()

    @Transaction
    suspend fun updateTechNews(techNews: List<TechnologyNews>) {
        clearTechNewsTable()
        insertAll(techNews)
    }
}