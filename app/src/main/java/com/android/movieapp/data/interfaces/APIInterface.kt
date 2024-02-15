package com.android.movieapp.data.interfaces

import com.android.movieapp.data.models.MovieDetailsResponse
import com.android.movieapp.data.models.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("/")
    suspend fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchString: String,
        @Query("page") pageNo: Int
    ): Response<MovieListResponse>


    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbID: String,
        @Query("apikey") apiKey: String,
    ): Response<MovieDetailsResponse>

}