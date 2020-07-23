package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao : BaseDao<SearchHistory> {

    @Query("SELECT * FROM SearchHistory")
    fun observeSearchHistory(): Flow<List<SearchHistory>>
}