package com.scan.moviesapp

import android.app.Application
import com.scan.moviesapp.di.AppComponent
import com.scan.moviesapp.di.AppModule
import com.scan.moviesapp.di.DaggerAppComponent

class MoviesApp: Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
    fun getAppComponent(): AppComponent {
        return appComponent
    }
}