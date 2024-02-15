package com.android.movieapp

import android.app.Application
import androidx.room.Room
import com.android.movieapp.data.di.AppComponent
import com.android.movieapp.data.di.DaggerAppComponent
import com.android.movieapp.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication:Application(){
    companion object {
        lateinit var database: AppDatabase
            private set
    }
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "movie_db"
        ).build()

        appComponent = DaggerAppComponent.builder().build()

    }
}