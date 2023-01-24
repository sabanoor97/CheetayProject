package com.faiz.baseproject.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.faiz.baseproject.base.BasePagingSource
import com.faiz.baseproject.di.model.Movie
import com.faiz.baseproject.di.model.Results
import com.faiz.baseproject.network.MovieApiService
import com.faiz.baseproject.utils.Constants
import com.faiz.baseproject.utils.update
import javax.inject.Inject

class MoviesPagingSource(
    private val apiService: MovieApiService,
) : PagingSource<Int, Results>(), IMoviePagingSource {

    private lateinit var query: MutableMap<String, String>

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun setQuery(query: MutableMap<String, String>) {
        this.query = query
    }

    private val totalItems = MutableLiveData<Long>()


    override fun getTotalItems(): LiveData<Long> {
        return totalItems
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            val nextPageNumber = params.key ?: 0
            val limit = query[Constants.LIMIT].toString().toInt()
            query[Constants.OFF_SET] = (limit * nextPageNumber).toString()
            val response = apiService.getAllMovies(
                map = (query ?: emptyMap()) as MutableMap<String, String>,
            )

            totalItems.update(response.total_pages)

            var nextPage: Int? = null
            var prevPage: Int? = null
//        if (response.hasNextPage)
//            nextPage = response.nextPage
//        if (response.hasPreviousPage)
//            prevPage = response.previousPage


            LoadResult.Page(
                data = response.results,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (exception: Exception) {
            totalItems.update(0)
            LoadResult.Error(exception)
        }
    }
}