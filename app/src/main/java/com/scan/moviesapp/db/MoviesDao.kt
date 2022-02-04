package com.scan.moviesapp.db

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import com.scan.moviesapp.model.MovieItem


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: ArrayList<MovieItem>)

    @Query("SELECT * FROM MovieItem")
    fun getAllMovies(): PagingSource<Int,MovieItem>

    @Query("SELECT * FROM MovieItem WHERE id = :movieId")
    fun getMovieByID(movieId: String): MovieItem?

    @Query("DELETE FROM MovieItem")
    fun delete()
}