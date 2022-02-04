package com.scan.moviesapp

import com.scan.moviesapp.adapter.MoviesPagingAdapter
import com.scan.moviesapp.model.MovieItem
import com.scan.moviesapp.ui.viewmodel.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class PagingTest {
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun repo() = runBlockingTest {
        val viewModel = Mockito.mock(MainActivityViewModel::class.java)
        val moviesAdapter = MoviesPagingAdapter()
        val movie1 = MovieItem(
            id = "51861093667",
            owner = "79651136@N04",
            secret = "174efd5fdb",
            server = "65535",
            farm = 66,
            title = "00220-5-sh1",
            ispublic = 1,
            isfriend = 0,
            isfamily = 0
        )
        val movie2 = MovieItem(
            id = "51862069441",
            owner = "128878149@N07",
            secret = "ee9d25d0e2",
            server = "65535",
            farm = 66,
            title = "Country landscape near Siponto, Apulia, Italy",
            ispublic = 1,
            isfriend = 0,
            isfamily = 0
        )

        val job = launch {
            viewModel.listResult.collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        advanceUntilIdle()

        Assert.assertEquals(listOf(movie1, movie2).toMutableList(), moviesAdapter.snapshot())
        job.cancel()
    }

}