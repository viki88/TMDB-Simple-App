package com.purwadhika.simplemovieappthemoviedb.network

import com.purwadhika.simplemovieappthemoviedb.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constant.BASE_URL)
        .build()

    fun getTheMovieDbApiService() = retrofit.create(TheMovieDbInterface::class.java)
}