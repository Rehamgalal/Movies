package com.scan.moviesapp.di

import androidx.paging.ExperimentalPagingApi
import com.scan.moviesapp.ui.MainActivity
import com.scan.moviesapp.ui.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton


@ExperimentalPagingApi
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(viewModel: MainActivityViewModel)
}