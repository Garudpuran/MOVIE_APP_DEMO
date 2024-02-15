package com.android.movieapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.movieapp.data.models.Search
@Dao
interface MoviesDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieList(data:Search)

    @Query("SELECT * FROM movie_db")
    suspend fun getAllMovies(): List<Search>
}