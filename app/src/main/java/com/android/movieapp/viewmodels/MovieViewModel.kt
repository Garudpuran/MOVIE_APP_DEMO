package com.android.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieapp.data.models.MovieDetailsResponse
import com.android.movieapp.data.models.Search
import com.android.movieapp.data.repositories.MovieRepo
import com.android.movieapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MovieRepo
) : ViewModel() {

    private val _movieLiveData = MutableLiveData<NetworkResult<List<Search>>>()
    val movieLiveData: LiveData<NetworkResult<List<Search>>> get() = _movieLiveData

    private var currentPage = 1
    private var isFetching = false

    fun getMovies( searchString: String) {
        if (isFetching) return
        viewModelScope.launch {
            try {
                _movieLiveData.postValue(NetworkResult.Loading())
                isFetching = true
                val uc = movieRepo.getMovies( searchString, currentPage)
                if(uc.isSuccessful&&uc.body()!!.Response == "True"){
                    _movieLiveData.postValue(NetworkResult.Success(uc.body()!!.Search))
                    currentPage++
                }else if(uc.body()!!.Response == "False") {
                    _movieLiveData.postValue(NetworkResult.Error(uc.body()!!.Error))
                }
                    else
                {
                    _movieLiveData.postValue(NetworkResult.Error(uc.message()))
                }
            }catch (e:Exception){
                _movieLiveData.postValue(NetworkResult.Error(e.message))
            }finally {
                isFetching = false
            }

        }
    }


    private val _movieDetailsLD = MutableLiveData<NetworkResult<MovieDetailsResponse>>()
    val movieDetailsLD: LiveData<NetworkResult<MovieDetailsResponse>> get() = _movieDetailsLD

    fun getMovieDetails(imdbID: String) {
        viewModelScope.launch {
            try {
                _movieDetailsLD.postValue(NetworkResult.Loading())
                val resp = movieRepo.getMovieDetails(imdbID)
                if(resp.isSuccessful&&resp.body()!!.Response == "True"){
                    _movieDetailsLD.postValue(NetworkResult.Success(resp.body()!!))
                }
                else
                {
                    _movieDetailsLD.postValue(NetworkResult.Error(resp.message()))
                }
            }catch (e:Exception){
                _movieDetailsLD.postValue(NetworkResult.Error(e.message))
            }
        }
    }
}
