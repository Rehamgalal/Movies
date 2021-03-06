package com.scan.moviesapp.model.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scan.moviesapp.api.MoviesApi
import com.scan.moviesapp.model.MovieItem
import java.lang.Exception
import javax.inject.Inject

class MoviesPagingDataSource @Inject constructor(private  val service:MoviesApi): PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val pageNumber = params.key ?: 1
            val response = service.getMovies("flickr.photos.search", "json", "50", "Color",pageNumber,20)
            val responseData = mutableListOf<MovieItem>()
            val data = response.body()?.photos?.photo ?: emptyList()
            responseData.addAll(data)
            val prevKey = if (pageNumber == 1) null else pageNumber - 1
            LoadResult.Page(
                data= responseData,
                prevKey = prevKey,
                nextKey = pageNumber.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}