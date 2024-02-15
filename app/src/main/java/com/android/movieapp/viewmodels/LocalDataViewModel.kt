package com.android.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieapp.data.local.MovieDataRepo
import com.android.movieapp.data.models.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalDataViewModel@Inject constructor(private val repository: MovieDataRepo) : ViewModel() {


    private val _insertionResult = MutableLiveData<Boolean>()
    val insertionResult: LiveData<Boolean> get() = _insertionResult

    fun insertMovieData(movieData: Search) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertMovieData(movieData)
                _insertionResult.postValue(true)
            } catch (e: Exception) {
                _insertionResult.postValue(false)
            }
        }
    }

    private val _savedData = MutableLiveData<List<Search>>()
    val savedData: LiveData<List<Search>> get() = _savedData

    fun getAllMoviesData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                _savedData.postValue(repository.getMovieData())
            } catch (e: Exception) {
                _savedData.postValue(emptyList())
            }
        }
    }

}