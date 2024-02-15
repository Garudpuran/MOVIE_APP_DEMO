package com.android.movieapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.movieapp.data.interfaces.APIInterface
import com.android.movieapp.data.models.MovieDetailsResponse
import com.android.movieapp.data.models.MovieListResponse
import com.android.movieapp.utils.Constants
import com.android.movieapp.utils.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class MovieRepo @Inject constructor(
    private val apiInterface: APIInterface
) {

    suspend fun getMovies(
        searchString: String,
        currentPage: Int
    ): Response<MovieListResponse> {
        return apiInterface.getMovies(Constants.APIKEY, searchString, currentPage)


    }


    private val _movieDetailsData = MutableLiveData<NetworkResult<MovieDetailsResponse>>()
    val movieDetailsData: LiveData<NetworkResult<MovieDetailsResponse>>
        get() = _movieDetailsData


    suspend fun getMovieDetails(imdbID: String):Response<MovieDetailsResponse> {
        return apiInterface.getMovieDetails(imdbID, Constants.APIKEY)
    }
}


