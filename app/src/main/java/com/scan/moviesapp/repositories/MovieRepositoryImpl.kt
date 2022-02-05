package com.scan.moviesapp.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.scan.moviesapp.api.MoviesApi
import com.scan.moviesapp.db.MoviesDataBase
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRepositoryImpl @Inject constructor(private val api: MoviesApi,private  val db: MoviesDataBase): MovieRepository{
    override fun getMovies() = Pager(config = PagingConfig(pageSize = 20),remoteMediator = MoviesRemoteMediator(api,db)) {
            db.moviesDao().getAllMovies()
        }.flow
    }
