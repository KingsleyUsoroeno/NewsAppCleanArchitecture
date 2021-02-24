package com.techkingsley.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.techkingsley.cache.model.CachedSearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao : BaseDao<CachedSearchHistory> {

    @Query("SELECT * FROM CachedSearchHistory")
    fun observeSearchHistory(): Flow<List<CachedSearchHistory>>
}