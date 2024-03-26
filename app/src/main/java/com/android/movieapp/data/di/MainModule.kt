package com.android.movieapp.data.di

import com.android.movieapp.data.interfaces.APIInterface
import com.android.movieapp.data.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MainModule {
    @Provides
    @Singleton
    fun provideAPIInterface(): APIInterface {
        return ApiClient().getClient().create(APIInterface::class.java)
    }
}