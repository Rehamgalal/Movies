package com.scan.moviesapp

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.scan.moviesapp.model.datasource.MoviesPagingDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test


@ExperimentalPagingApi
class PagingTest {
    private val movieFactory = MovieFactory()
    private val fakeMovies = listOf(
            movieFactory.createMovies(),
            movieFactory.createMovies(),
            movieFactory.createMovies()
    )
    private val fakeApi = FakeMoviesApi().apply {
        fakeMovies.forEach { post -> addPost(post) }
    }
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun repo() = runBlockingTest {
        val pagingSource = MoviesPagingDataSource(fakeApi)

        Assert.assertNotEquals(PagingSource.LoadResult.Page(listOf(fakeMovies[0],fakeMovies[1]),fakeMovies[0].id,fakeMovies[1].id), pagingSource.load(
                PagingSource.LoadParams.Refresh(null,2,false)))
    }

}