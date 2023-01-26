package com.faiz.baseproject.localcache

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faiz.baseproject.localcache.entity.FavItems
import kotlinx.coroutines.flow.Flow

interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favEntity: FavItems): Long

    @Query("SELECT * FROM movie_table")
    fun getAllOfflineMovies(): Flow<List<FavItems>>

    @Delete
    suspend fun delete(favEntity: FavItems)

}