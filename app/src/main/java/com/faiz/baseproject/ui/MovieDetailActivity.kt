package com.faiz.baseproject.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faiz.baseproject.BuildConfig
import com.faiz.baseproject.databinding.ActivityMovieDetailBinding
import com.faiz.baseproject.utils.Constants
import com.hexagram.yahuda.utils.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val binding: ActivityMovieDetailBinding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getIntentData()
    }

    private fun getIntentData() {
        var image = intent.getStringExtra(Constants.IMAGE)
        var title = intent.getStringExtra(Constants.TITLE)
        var overview = intent.getStringExtra(Constants.OVERVIEW)
//        var imageUrl = Constants.POSTER_BASE_URL + image
        binding.apply {
            tvTitle.text = title
//            ivPoster.load(imageUrl)
            tvDescription.text = overview
        }
    }
}