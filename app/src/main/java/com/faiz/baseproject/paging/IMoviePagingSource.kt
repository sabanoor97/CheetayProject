package com.faiz.baseproject.paging

import androidx.lifecycle.LiveData
import com.faiz.baseproject.di.IPagingSource

interface IMoviePagingSource : IMoviesPagingMetaData, IPagingSource {
    fun setQuery(query: MutableMap<String, String>)
}

interface IMoviesPagingMetaData {
    fun getTotalItems(): LiveData<Long>
}