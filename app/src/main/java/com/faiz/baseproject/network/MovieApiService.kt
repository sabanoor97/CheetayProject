package com.faiz.baseproject.network

import com.faiz.baseproject.di.model.Movie
import com.faiz.baseproject.network.adapter.ApiResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieApiService {

    @GET("movie/popular")
    @JvmSuppressWildcards
    suspend fun getAllMovies(
        @QueryMap map: MutableMap<String, String>
    ): Movie

    @GET("search/movie")
    @JvmSuppressWildcards
    suspend fun searchMovieListing(
        @QueryMap map: MutableMap<String, String>
    ): ApiResponse<Movie>
}