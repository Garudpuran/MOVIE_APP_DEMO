package com.android.movieapp

import android.app.Application
import com.android.movieapp.data.di.AppComponent
import com.android.movieapp.data.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication:Application(){
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()

    }
}