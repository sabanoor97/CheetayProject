package com.faiz.baseproject.localcache

import androidx.room.*
import com.faiz.baseproject.localcache.entity.MovieItems
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleEntity: MovieItems): Long

    @Query("SELECT * FROM movie_table")
    fun getAllOfflineMovies(): Flow<List<MovieItems>>

    @Delete
    suspend fun delete(movieEntity: MovieItems)

}