package com.faiz.baseproject.localcache.entity

import androidx.room.Entity

@Entity(tableName = "movie_table")
data class MovieItems(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Long,
    val video: Boolean,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val title: String,
    val release_date: String,
    val popularity: Double,
)
