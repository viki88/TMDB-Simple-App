package com.purwadhika.simplemovieappthemoviedb.ui.detailmovie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.purwadhika.simplemovieappthemoviedb.DatabaseHelper
import com.purwadhika.simplemovieappthemoviedb.database.table.FavMovie
import kotlinx.coroutines.launch

class DetailMovieViewModel(application: Application) : AndroidViewModel(application) {

    var favMovieLiveData = MutableLiveData<List<FavMovie>>()
    var statusProgressDBLiveData = MutableLiveData<StatusProgressDb>()

    private val db = DatabaseHelper.getDb(application)

    fun loadAllFavMovie(){
        viewModelScope.launch {
            favMovieLiveData.postValue(db.favMovieDao().getAllFavMovie())
            statusProgressDBLiveData.postValue(StatusProgressDb.LOAD_SUCCESS)
        }
    }

    fun savFavMovie(favMovie: FavMovie){
        viewModelScope.launch {
            db.favMovieDao().insertFavMovie(favMovie)
            favMovieLiveData.postValue(db.favMovieDao().getAllFavMovie())
            statusProgressDBLiveData.postValue(StatusProgressDb.SAVE_SUCCESS)
        }
    }

    fun deleteFavMovie(idMovie :Long){
        viewModelScope.launch {
            db.favMovieDao().deleteFavMovieById(idMovie)
            favMovieLiveData.postValue(db.favMovieDao().getAllFavMovie())
            statusProgressDBLiveData.postValue(StatusProgressDb.DELETE_SUCCESS)
        }
    }

    enum class StatusProgressDb{
        SAVE_SUCCESS, DELETE_SUCCESS, LOAD_SUCCESS
    }
}