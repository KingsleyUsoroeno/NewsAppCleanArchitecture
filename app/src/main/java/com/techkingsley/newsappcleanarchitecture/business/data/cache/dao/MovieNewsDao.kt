package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieNewsDao : BaseDao<Movies> {

    @Query("SELECT * FROM movies")
    fun observeMovieNews(): Flow<List<Movies>>
}