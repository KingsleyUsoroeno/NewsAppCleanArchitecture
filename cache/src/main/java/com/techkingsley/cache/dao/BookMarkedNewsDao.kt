package com.techkingsley.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.techkingsley.cache.model.CachedBookMarkNews
import kotlinx.coroutines.flow.Flow

@Dao
interface BookMarkedNewsDao : BaseDao<CachedBookMarkNews> {

    @Query("SELECT * FROM CachedBookMarkNews")
    fun getAllBookMarkedNews(): Flow<List<CachedBookMarkNews>>

}