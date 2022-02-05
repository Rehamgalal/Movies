package com.scan.moviesapp.ui.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scan.moviesapp.MoviesApp
import com.scan.moviesapp.api.MoviesApi
import com.scan.moviesapp.db.MoviesDataBase
import com.scan.moviesapp.model.MovieItem
import com.scan.moviesapp.repositories.MovieRepository
import com.scan.moviesapp.repositories.MovieRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class MainActivityViewModel(@NonNull application: Application) : AndroidViewModel(application){
    private val repository:MovieRepository
    var listResult: Flow<PagingData<MovieItem>>

    @Inject
    lateinit var service: MoviesApi

    @Inject
    lateinit var db: MoviesDataBase

    init {
        (application as MoviesApp).getAppComponent().inject(this)
        repository = MovieRepositoryImpl(service,db)
        listResult  = getListPosts()
    }

    private fun getListPosts() : Flow<PagingData<MovieItem>> {
        return  repository.getMovies().cachedIn(viewModelScope)
    }
}