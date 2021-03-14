package com.techkingsley.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.techkingsley.cache.models.CachedBookMarkNews
import kotlinx.coroutines.flow.Flow

@Dao
interface BookMarkedNewsDao : BaseDao<CachedBookMarkNews> {

    @get:Query("SELECT * FROM CachedBookMarkNews")
    val observeBookNews: Flow<List<CachedBookMarkNews>>
}