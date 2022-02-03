package com.scan.moviesapp.utils

import com.scan.moviesapp.model.MovieItem

interface OnMovieClicked {
    fun onClick(movie: MovieItem)
}