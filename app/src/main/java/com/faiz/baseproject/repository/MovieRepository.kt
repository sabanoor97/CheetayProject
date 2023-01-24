package com.faiz.baseproject.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.faiz.baseproject.di.model.Movie
import com.faiz.baseproject.di.model.Results
import com.faiz.baseproject.network.DataState
import com.faiz.baseproject.network.MovieApiService
import com.faiz.baseproject.network.adapter.*
import com.faiz.baseproject.paging.IMoviePagingSource
import com.faiz.baseproject.utils.ResponseCode
import com.faiz.baseproject.utils.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Provider

class MovieRepository @Inject constructor(
    private val apiService: MovieApiService,
    private val stringUtils: StringUtils,
) : IMovieRepository {

    @Inject
    lateinit var pagingSourceProvider: Provider<IMoviePagingSource>
    private var pagingSource: IMoviePagingSource? = null

    override fun getAllMovies(
        dispatcher: CoroutineDispatcher,
        map: MutableMap<String, String>,
    ): Flow<PagingData<Results>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            pagingSource = pagingSourceProvider.get()
                .apply {
                    setQuery(map)
                }
            pagingSource as PagingSource<Int, Results>
        }.flow
            .catch {
                Log.d("", "")
            }
    }

    override fun getTotalItems(): LiveData<Long> {
        return pagingSource?.getTotalItems()!!
    }
//    override suspend fun getAllMovies(
//        dispatcher: CoroutineDispatcher,
//        map: MutableMap<String, String>
//    ): Flow<DataState<Movie>> {
//        return flow {
//            apiService.getAllMovies(
//                map = map
//            ).onSuccess {
//                data?.let { emit(DataState.success(it)) }
//            }
//                .onError {
//                    emit(
//                        DataState.error<Movie>(
//                            response.code(),
//                            message()
//                        )
//                    )
//                }
//                .onException {
//                    emit(
//                        DataState.error<Movie>(
//                            ResponseCode.GENERAL_ERROR.value,
//                            stringUtils.somethingWentWrong()
//                        )
//                    )
//                }
//                .onNetworkError {
//                    emit(
//                        DataState.error<Movie>(
//                            ResponseCode.CONNECTION_TIMEOUT.value,
//                            stringUtils.noNetworkErrorMessage()
//                        )
//                    )
//                }
//        }.flowOn(dispatcher)
//    }

    override suspend fun searchMovieListing(
        dispatcher: CoroutineDispatcher,
        map: MutableMap<String, String>
    ): Flow<DataState<Movie>> {
        return flow {
            apiService.searchMovieListing(
                map = map
            ).onSuccess {
                data?.let { emit(DataState.success(it)) }
            }
                .onError {
                    emit(
                        DataState.error<Movie>(
                            response.code(),
                            message()
                        )
                    )
                }
                .onException {
                    emit(
                        DataState.error<Movie>(
                            ResponseCode.GENERAL_ERROR.value,
                            stringUtils.somethingWentWrong()
                        )
                    )
                }
                .onNetworkError {
                    emit(
                        DataState.error<Movie>(
                            ResponseCode.CONNECTION_TIMEOUT.value,
                            stringUtils.noNetworkErrorMessage()
                        )
                    )
                }
        }.flowOn(dispatcher)
    }
}
