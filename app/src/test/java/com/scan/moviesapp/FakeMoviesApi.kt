package com.scan.moviesapp

import com.scan.moviesapp.api.MoviesApi
import com.scan.moviesapp.model.MovieItem
import com.scan.moviesapp.model.MoviesResponse
import com.scan.moviesapp.model.PhotosModel
import retrofit2.Response
import retrofit2.http.Query
import java.io.IOException
import java.lang.Integer.min

class FakeMoviesApi: MoviesApi {

    private val model = mutableMapOf<String, SubMovie>()
    private var failureMsg: String? = null
    fun addPost(post: MovieItem) {
        val subreddit = model.getOrPut(post.id) {
            SubMovie(items = arrayListOf())
        }
        subreddit.items.add(post)
    }

    private fun findPosts(
            submovie: String,
            limit: Int,
            after: String? = null,
            before: String? = null
    ): List<MovieItem> {
        if (before != null) return emptyList()

        val subMovie = findSubReddit(submovie)
        return subMovie.findPosts(limit, after)
    }

    private fun findSubReddit(subreddit: String) =
            model.getOrDefault(subreddit, SubMovie())

    override suspend fun getMovies(@Query("method")method:String,@Query("format")format:String,@Query("nojsoncallback")noJsoncallback:String,@Query("text")text:String,
                                   @Query("page")page:Int, @Query("per_page")perPage:Int): Response<PhotosModel>{
        failureMsg?.let {
            throw IOException(it)
        }
        val items = findPosts(method, perPage, (page-1).toString(), (page+1).toString())
        items.lastOrNull()
        return Response.success(1, PhotosModel(MoviesResponse(page,2,perPage,2, arrayListOf(MovieItem("1","66","2334544","55555",1,"title",0,0,0)))))
    }

    private class SubMovie(val items: MutableList<MovieItem> = arrayListOf()) {
        fun findPosts(limit: Int, after: String?): List<MovieItem> {
            if (after == null) {
                return items.subList(0, min(items.size, limit))
            }
            val index = items.indexOfFirst { it.id == after }
            if (index == -1) {
                return emptyList()
            }
            val startPos = index + 1
            return items.subList(startPos, min(items.size, startPos + limit))
        }
    }
}