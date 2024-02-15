package com.android.movieapp.data.local

import com.android.movieapp.data.models.Search

class MovieDataRepo(private val moviesDataDao: MoviesDataDao) {

    suspend fun insertMovieData(movieData: Search) {
        moviesDataDao.insertMovieList(movieData)
    }

    suspend fun getMovieData():List<Search> {
        return moviesDataDao.getAllMovies()
    }
}