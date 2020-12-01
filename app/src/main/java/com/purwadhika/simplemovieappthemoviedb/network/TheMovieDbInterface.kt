package com.purwadhika.simplemovieappthemoviedb.network

import com.purwadhika.simplemovieappthemoviedb.network.response.TrendingMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbInterface{

    @GET("trending/movie/week")
    fun getAllTrending(@Query("api_key") apiKey :String) :Call<TrendingMovieResponse>
}