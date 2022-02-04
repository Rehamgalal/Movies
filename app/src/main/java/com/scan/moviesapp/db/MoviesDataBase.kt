package com.scan.moviesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scan.moviesapp.model.MovieItem
import com.scan.moviesapp.other.Constants.DATABASE_NAME
import com.scan.moviesapp.other.Constants.DATABASE_VERSION


@Database(entities = arrayOf(MovieItem::class),version = DATABASE_VERSION)
abstract class MoviesDataBase: RoomDatabase(){
    abstract fun moviesDao(): MoviesDao

    companion object {
        fun buildDatabase(context: Context): MoviesDataBase {
            return Room.databaseBuilder(context, MoviesDataBase::class.java, DATABASE_NAME)
                .build()
        }
    }
}