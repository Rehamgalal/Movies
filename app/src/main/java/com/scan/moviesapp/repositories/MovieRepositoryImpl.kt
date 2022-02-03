package com.scan.moviesapp.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.scan.moviesapp.api.MoviesApi
import com.scan.moviesapp.model.datasource.MoviesPagingDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MoviesApi): MovieRepository{
    override fun getMovies() = Pager(PagingConfig(pageSize = 20)) {
            MoviesPagingDataSource(api)
        }.flow
    }
