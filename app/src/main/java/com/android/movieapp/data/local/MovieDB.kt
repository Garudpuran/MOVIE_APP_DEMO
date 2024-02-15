package com.android.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.movieapp.data.models.Search

@Database(entities = [Search::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDataDao
}