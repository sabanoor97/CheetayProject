package com.faiz.baseproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.faiz.baseproject.BuildConfig
import com.faiz.baseproject.databinding.RvMovieItemsBinding
import com.faiz.baseproject.di.model.Movie
import com.faiz.baseproject.di.model.Results
import com.faiz.baseproject.enum.ClickTypeEnum
import com.faiz.baseproject.utils.Constants
import com.hexagram.yahuda.utils.load
import com.hexagram.yahuda.utils.showToast

class MovieListAdapter
    (
    private val onMovieItemClick: (Results) -> Unit,
    private val onFavIconClick: (Results) -> Unit,
) : PagingDataAdapter<Results, MovieListAdapter.MovieViewHolder>(MovieDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = RvMovieItemsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindItems(item)
            holder.setOnClickListener(item)
        }
    }

    inner class MovieViewHolder(private val binding: RvMovieItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(movies: Results) {
            binding.apply {
                val context = binding.tvName.context
                var imageUrl = Constants.POSTER_BASE_URL + movies.poster_path
                tvName.text = movies.title
                ivPoster.load(imageUrl)
            }
        }

        fun setOnClickListener(movies: Results) {
            binding.root.setOnClickListener {
                onMovieItemClick(movies)
            }
            binding.icFav.setOnClickListener {
                onMovieItemClick(movies)
            }
        }
    }

    companion object {
        private object MovieDiffUtils : DiffUtil.ItemCallback<Results>() {
            override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
                // Id is unique.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
                return oldItem == newItem
            }
        }
    }
}