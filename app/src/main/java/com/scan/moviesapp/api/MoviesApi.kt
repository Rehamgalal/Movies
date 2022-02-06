package com.scan.moviesapp.api

import com.scan.moviesapp.model.PhotosModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApi {

    @GET("services/rest/")
    suspend fun getMovies(@Query("method")method:String,@Query("format")format:String,@Query("nojsoncallback")noJsoncallback:String,@Query("text")text:String,
    @Query("page")page:Int, @Query("per_page")perPage:Int):Response<PhotosModel>
}