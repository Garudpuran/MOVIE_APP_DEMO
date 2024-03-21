package com.android.movieapp.data.di

import com.android.movieapp.MyApplication
import com.android.movieapp.data.interfaces.APIInterface
import com.android.movieapp.data.local.AppDatabase
import com.android.movieapp.data.local.MovieDataRepo
import com.android.movieapp.data.local.MoviesDataDao
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

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MoviesDataDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepo(moviesDataDao: MoviesDataDao): MovieDataRepo {
        return MovieDataRepo(moviesDataDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return MyApplication.database
    }


}