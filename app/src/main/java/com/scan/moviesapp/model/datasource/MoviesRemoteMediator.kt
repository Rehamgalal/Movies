package com.scan.moviesapp.model.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.scan.moviesapp.api.MoviesApi
import com.scan.moviesapp.db.MoviesDataBase
import com.scan.moviesapp.model.MovieItem
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

@ExperimentalPagingApi
class MoviesRemoteMediator @Inject constructor(private  val service: MoviesApi, private  val db: MoviesDataBase): RemoteMediator<Int,MovieItem>() {

    val userDao = db.moviesDao()
    var pageNumber =  1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieItem>
    ): MediatorResult {


        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.id
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = service.getMovies(
                "flickr.photos.search", "json", "50", "Color",pageNumber,20
            )
            val responseData = response.body()?.photos

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.delete()
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                userDao.insert(responseData?.photo!!)
            }

            pageNumber = responseData?.page?.plus(1)!!

            MediatorResult.Success(
                endOfPaginationReached = responseData.page.toLong() == responseData.total
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}