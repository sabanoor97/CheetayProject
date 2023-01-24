package com.faiz.baseproject.di.model

data class Movie(
    val page: Int,
    val total_pages: Long,
    val total_results: Int,
    val results: List<Results>
)
