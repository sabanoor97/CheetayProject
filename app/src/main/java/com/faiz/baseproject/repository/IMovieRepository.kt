package com.faiz.baseproject.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.faiz.baseproject.di.model.Movie
import com.faiz.baseproject.di.model.Results
import com.faiz.baseproject.network.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
//    suspend fun getAllMovies(
//        dispatcher: CoroutineDispatcher,
//        map: MutableMap<String, String>,
//    ): Flow<DataState<Movie>>

    suspend fun searchMovieListing(
        dispatcher: CoroutineDispatcher,
        map: MutableMap<String, String>,
    ): Flow<DataState<Movie>>

    fun getAllMovies(
        dispatcher: CoroutineDispatcher,
        map: MutableMap<String, String>,
    ): Flow<PagingData<Results>>

    fun getTotalItems(): LiveData<Long>
}