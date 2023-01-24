package com.faiz.baseproject.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.faiz.baseproject.R
import com.faiz.baseproject.base.BaseViewModel
import com.faiz.baseproject.di.model.Movie
import com.faiz.baseproject.di.model.Results
import com.faiz.baseproject.network.DataState
import com.faiz.baseproject.repository.IMovieRepository
import com.faiz.baseproject.utils.CommonMethods
import com.faiz.baseproject.utils.Event
import com.faiz.baseproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val apiRepository: IMovieRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    // get all movies
    private var _movies: MutableLiveData<Event<Resource<Movie>>> =
        MutableLiveData<Event<Resource<Movie>>>()
    val movies: LiveData<Event<Resource<Movie>>> = _movies

    // search movies
    private var _searchMovies: MutableLiveData<Event<Resource<Movie>>> =
        MutableLiveData<Event<Resource<Movie>>>()
    val searchMovies: LiveData<Event<Resource<Movie>>> = _searchMovies

    fun getAllMovies(
        map: MutableMap<String, String>,
    ): Flow<PagingData<Results>> = apiRepository.getAllMovies(
        dispatcher = Dispatchers.IO,
        map = map,
    ).cachedIn(viewModelScope)

    fun getTotalItems(): LiveData<Long> {
        return apiRepository.getTotalItems()
    }
//    fun getAllMovies(
//        map: MutableMap<String, String>,
//    ) {
//        viewModelScope.launch {
//            apiRepository.getAllMovies(
//                dispatcher = Dispatchers.IO,
//                map = map
//            ).onStart {
//                _movies.postValue(Event(Resource.loading(data = null)))
//            }.collect {
//                when (it) {
//                    is DataState.Success -> {
//                        _movies.postValue(Event(Resource.success(data = it.data)))
//                    }
//                    is DataState.Error -> {
//                        _movies.postValue(
//                            Event(
//                                Resource.error(
//                                    data = null,
//                                    CommonMethods.getErrorMessage(
//                                        context = context,
//                                        errorCode = it.code,
//                                        errorMessage = it.message,
//                                        context.getString(R.string.error_occured)
//                                    )
//                                )
//                            )
//                        )
//                    }
//                    else -> {}
//                }
//            }
//        }
//    }

    fun searchMovieListing(
        map: MutableMap<String, String>,
    ) {
        viewModelScope.launch {
            apiRepository.searchMovieListing(
                dispatcher = Dispatchers.IO,
                map = map
            ).onStart {
                _searchMovies.postValue(Event(Resource.loading(data = null)))
            }.collect {
                when (it) {
                    is DataState.Success -> {
                        _searchMovies.postValue(Event(Resource.success(data = it.data)))
                    }
                    is DataState.Error -> {
                        _searchMovies.postValue(
                            Event(
                                Resource.error(
                                    data = null,
                                    CommonMethods.getErrorMessage(
                                        context = context,
                                        errorCode = it.code,
                                        errorMessage = it.message,
                                        context.getString(R.string.error_occured)
                                    )
                                )
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}