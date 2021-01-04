package com.purwadhika.simplemovieappthemoviedb

import android.content.Context
import androidx.room.Room
import com.purwadhika.simplemovieappthemoviedb.database.AppDatabase

class DatabaseHelper {
    companion object{
        private const val DB_NAME = "movie_app_db"

        fun getDb(context: Context) : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }
}