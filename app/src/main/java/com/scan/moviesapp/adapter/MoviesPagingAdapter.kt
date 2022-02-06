package com.scan.moviesapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.scan.moviesapp.databinding.AdItemBinding
import com.scan.moviesapp.databinding.MovieItemBinding
import com.scan.moviesapp.model.MovieItem
import com.scan.moviesapp.utils.OnAdClicked
import com.scan.moviesapp.utils.OnMovieClicked
import javax.inject.Inject


class MoviesPagingAdapter @Inject constructor(): PagingDataAdapter<MovieItem, RecyclerView.ViewHolder>(
    MoviesComprator
) {

    private var movieListener: OnMovieClicked ? = null
    private var adListener: OnAdClicked ? = null
    private val ADD_TYPE = 1
    private val MOVIE_TYPE =  2
    fun setListener(movieListener: OnMovieClicked, adListener: OnAdClicked) {
        this.movieListener = movieListener
        this.adListener  = adListener
    }
    object MoviesComprator : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem) =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_TYPE) {
            (holder as MoviesViewHolder).bind(getItem(position)!!)
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MOVIE_TYPE) MoviesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ), movieListener!!
        )
        else AddViewHolder(
            AdItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), adListener!!
        )
    }


    override fun getItemViewType(position: Int): Int {
        return if (position % 6 == 0)  ADD_TYPE else MOVIE_TYPE
    }
    class MoviesViewHolder(
        private val binding: MovieItemBinding,
        private val listener: OnMovieClicked
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieItem){
            binding.item = item
             itemView.setOnClickListener {
                    listener.onClick(item)
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.R)
    class AddViewHolder(private val binding: AdItemBinding, private val listener: OnAdClicked): RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            val adView = AdView(itemView.context)
            binding.adViewContainer.addView(adView)
                adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"
                adView.adSize = AdSize.BANNER
                val adRequest = AdRequest.Builder().build()
                adView.loadAd(adRequest)
        }
    }
    }

