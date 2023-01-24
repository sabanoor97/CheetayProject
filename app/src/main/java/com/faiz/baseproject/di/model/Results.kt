package com.faiz.baseproject.di.model

data class Results(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Long,
    val video: Boolean,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val popularity: Double,
//    val genre_ids: List<GenreIds>
)
