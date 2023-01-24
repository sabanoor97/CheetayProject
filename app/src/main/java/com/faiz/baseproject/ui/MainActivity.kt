package com.faiz.baseproject.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faiz.baseproject.BuildConfig
import com.faiz.baseproject.R
import com.faiz.baseproject.databinding.ActivityMainBinding
import com.faiz.baseproject.databinding.ActivityMovieDetailBinding
import com.faiz.baseproject.di.model.Movie
import com.faiz.baseproject.di.model.Results
import com.faiz.baseproject.enum.ClickTypeEnum
import com.faiz.baseproject.ext.isInternetAccessible
import com.faiz.baseproject.ui.adapter.MovieListAdapter
import com.faiz.baseproject.utils.Constants
import com.faiz.baseproject.utils.Status
import com.faiz.baseproject.viewmodel.MovieViewModel
import com.hexagram.yahuda.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.Credentials

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MovieViewModel by viewModels()
    var movieApiKey = BuildConfig.MOVIE_API_KEY
    private val OFF_SET = 0
    private val LIMIT = 10
    private var map: MutableMap<String, String> = mutableMapOf()
    private lateinit var moviesPagingAdapter: MovieListAdapter
    private var searchItem: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAdapter()
        getResults()
        setObserver()
        setOnclickListener()
    }

    private fun setAdapter() {
        moviesPagingAdapter = MovieListAdapter(
            onMovieItemClick(),
            onFavIconClick()
        )

        binding.rvMovies.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = moviesPagingAdapter
        }

        lifecycleScope.launch {
            moviesPagingAdapter.addLoadStateListener {
                if (it.refresh == LoadState.Loading) {
                    binding.progress.show()
                } else {
                    binding.progress.hide()
                }
            }
        }
    }

    private fun fetchMovies() {
        map[Constants.LIMIT] = LIMIT.toString()
        map[Constants.OFF_SET] = OFF_SET.toString()
        lifecycleScope.launch {
            viewModel.getAllMovies(
                map = map
            ).catch {
                Log.d("", "")
            }.collectLatest { pagingData ->
                viewModel.getTotalItems().observe(this@MainActivity) { totalCount ->
                    if (totalCount == 0L) {
                        binding.progress.hide()
                        binding.rvMovies.hide()
                    } else {
                        binding.rvMovies.show()
                    }
                }
                moviesPagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun getResults() {
        if (isInternetAccessible()) {
            fetchMovies()
            binding.etSearch.show()
            binding.btnSearch.show()
        } else {
            binding.progress.hide()
            binding.etSearch.hide()
            binding.btnSearch.hide()
            showToast(getString(R.string.message_no_network_connected_str))
        }
    }

    private fun setOnclickListener() {
        binding.apply {
            btnSearch.setOnClickListener {
                getSearchResults()
            }
        }
    }

    private fun getSearchResults() {
        searchItem = binding.etSearch.text.toString()
        if (searchItem.isEmpty()) {
            showToast(getString(R.string.please_enter_something))
        }
        val map = mutableMapOf<String, String>(
            Constants.API_KEY to movieApiKey,
            Constants.SEARCH_QUERY to searchItem,
        )
        viewModel.searchMovieListing(
            map = map
        )
    }

    private fun onFavIconClick(): (Results) -> Unit = {
        favourite(it)
    }

    private fun onMovieItemClick(): (Results) -> Unit = { results ->
        var bundle = Bundle()
        bundle.putString(Constants.TITLE, results.title)
        bundle.putString(Constants.IMAGE, results.poster_path)
        bundle.putString(Constants.OVERVIEW, results.overview)
        startActivityBundle(MovieDetailActivity::class.java, bundle)
    }


    private fun favourite(results: Results) {
    }

    private fun getAllMovies() {
        val map = mutableMapOf<String, String>(
            Constants.API_KEY to movieApiKey,
        )
        viewModel.getAllMovies(map = map)
    }

    private fun setObserver() {
//        fetchMoviesObserver()
        searchMoviesObserver()
    }
//
//    private fun fetchMoviesObserver() {
//        viewModel.movies.observe(this) {
//            when (it.peekContent().status) {
//                Status.SUCCESS -> {
//                    binding.progress.hide()
//                    fetchMovies(it.peekContent().data)
//                }
//                Status.LOADING -> {
//                    binding.progress.show()
//                }
//                Status.ERROR -> {
//                    showToast(getString(R.string.message_something_went_wrong_str))
//                    binding.progress.hide()
//                }
//            }
//        }
//    }

    private fun searchMoviesObserver() {
        viewModel.searchMovies.observe(this) {
            when (it.peekContent().status) {
                Status.SUCCESS -> {
                    binding.progress.hide()
                    onSearchResponseSuccess(it.peekContent().data)
                }
                Status.LOADING -> {
                    binding.progress.show()
                }
                Status.ERROR -> {
                    showToast(getString(R.string.message_something_went_wrong_str))
                    binding.progress.hide()
                }
            }
        }
    }

    private fun onSearchResponseSuccess(data: Movie?) {
        if (data?.results.isNullOrEmpty()) {
            showToast(getString(R.string.no_results_found))
        }
//        moviesPagingAdapter.submitData(data?.results)
    }
}