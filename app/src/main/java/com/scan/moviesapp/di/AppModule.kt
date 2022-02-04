package com.scan.moviesapp.di

import android.app.Application
import com.scan.moviesapp.api.MoviesApi
import com.scan.moviesapp.db.MoviesDataBase
import com.scan.moviesapp.other.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {
    @Singleton
    @Provides
    fun getRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        val requestInterceptor = Interceptor {chain ->
            val url = chain.request().url.newBuilder().addQueryParameter("api_key", Constants.API_KEY).build()
            val request = chain.request().newBuilder().url(url).build()
            return@Interceptor chain.proceed(request)
        }
        return OkHttpClient.Builder().addInterceptor(requestInterceptor).connectTimeout(60,TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun getRetrofitService(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Singleton
    @Provides
    fun providesMoviesDatabase(): MoviesDataBase {
        return MoviesDataBase.buildDatabase(application.applicationContext)
    }
}