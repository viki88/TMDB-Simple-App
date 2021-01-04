package com.purwadhika.simplemovieappthemoviedb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.purwadhika.simplemovieappthemoviedb.database.dao.FavMovieDAO
import com.purwadhika.simplemovieappthemoviedb.database.table.FavMovie

@Database(entities = [FavMovie::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun favMovieDao() :FavMovieDAO
}