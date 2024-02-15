package com.android.movieapp.data.di

import android.app.Application
import androidx.room.Room
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
    fun provideMovieDao(appDatabase: AppDatabase): MoviesDataDao {
        return appDatabase.movieDao()
    }

    @Provides
    fun provideMovieRepo(moviesDataDao: MoviesDataDao): MovieDataRepo {
        return MovieDataRepo(moviesDataDao)
    }

    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "movie_db"
        ).build()
    }


}