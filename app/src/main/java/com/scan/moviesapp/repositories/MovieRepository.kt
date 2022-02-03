package com.scan.moviesapp.repositories

import androidx.paging.PagingData
import com.scan.moviesapp.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(): Flow<PagingData<MovieItem>>
}