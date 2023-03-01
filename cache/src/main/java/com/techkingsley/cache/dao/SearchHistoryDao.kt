package com.techkingsley.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.techkingsley.cache.dao.base.BaseDao
import com.techkingsley.cache.entities.CacheSearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao : BaseDao<CacheSearchHistory> {

    @Query("SELECT * FROM CacheSearchHistory")
    fun observeSearchHistory(): Flow<List<CacheSearchHistory>>
}