package com.techkingsley.newsappcleanarchitecture.business.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieNewsDao : BaseDao<Movies> {

    @Query("SELECT * FROM movies")
    fun observeMovieNews(): Flow<List<Movies>>

    @Query("DELETE FROM movies")
    suspend fun clearMovieRecord()

    @Transaction
    suspend fun updateMovies(movies: List<Movies>) {
        clearMovieRecord()
        insertAll(movies)
    }
}