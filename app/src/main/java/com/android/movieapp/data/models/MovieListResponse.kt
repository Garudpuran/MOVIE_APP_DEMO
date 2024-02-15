package com.android.movieapp.data.models

data class MovieListResponse(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String,
    var Error:String?=""
)