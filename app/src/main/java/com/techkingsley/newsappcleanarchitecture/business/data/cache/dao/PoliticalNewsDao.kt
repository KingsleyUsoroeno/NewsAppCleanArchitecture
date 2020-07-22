package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import kotlinx.coroutines.flow.Flow

@Dao
interface PoliticalNewsDao : BaseDao<PoliticalNews> {

    @Query("SELECT * FROM politicalnews")
    fun observePoliticalNews(): Flow<List<PoliticalNews>>
}