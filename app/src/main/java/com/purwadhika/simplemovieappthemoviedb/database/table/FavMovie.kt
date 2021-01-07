package com.purwadhika.simplemovieappthemoviedb.database.table

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class FavMovie(
    @PrimaryKey(autoGenerate = true) var id:Long = 0,
    @ColumnInfo(name = "idmovie") var idmovie :Long,
    @ColumnInfo(name = "title") var title :String,
    @ColumnInfo(name = "imageurl") var imageUrl :String,
    @ColumnInfo(name = "desc") var desc :String
) :Parcelable