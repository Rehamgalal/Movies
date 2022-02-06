package com.scan.moviesapp

import com.scan.moviesapp.model.MovieItem
import java.util.concurrent.atomic.AtomicInteger

class MovieFactory {

    private val counter = AtomicInteger(0)

    fun createMovies(): MovieItem {
        val id = counter.incrementAndGet()

        return MovieItem(id.toString(),"",""
                ,"",0,"title", 0,0,0)
    }
}