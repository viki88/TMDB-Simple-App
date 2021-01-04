package com.purwadhika.simplemovieappthemoviedb.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.purwadhika.simplemovieappthemoviedb.database.table.FavMovie

@Dao
interface FavMovieDAO {

    @Query("SELECT * FROM FavMovie")
    suspend fun getAllFavMovie() :List<FavMovie>

    @Insert
    suspend fun insertFavMovie(favMovie: FavMovie)

    @Query("DELETE FROM FavMovie WHERE idmovie = :idMovie")
    suspend fun deleteFavMovieById(idMovie :Long)

}