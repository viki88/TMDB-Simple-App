package com.purwadhika.simplemovieappthemoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.purwadhika.simplemovieappthemoviedb.network.ApiService
import com.purwadhika.simplemovieappthemoviedb.network.response.TrendingMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var progressBarView :ProgressBar
    lateinit var textResponseView :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBarView = findViewById(R.id.progress_bar)
        textResponseView = findViewById(R.id.textview)

        loadTrendingMovie()
    }
    
    fun loadTrendingMovie(){
        progressBarView.visibility = View.VISIBLE
        ApiService().getTheMovieDbApiService().getAllTrending(Constant.API_KEY)
                .enqueue(object :Callback<TrendingMovieResponse>{
                    override fun onFailure(call: Call<TrendingMovieResponse>, t: Throwable) {
                        progressBarView.visibility = View.GONE
                        Log.i("TMDB", "onFailure: ${t.message}")
                        Toast.makeText(this@MainActivity, "error response : ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<TrendingMovieResponse>, response: Response<TrendingMovieResponse>) {
                        progressBarView.visibility = View.GONE

                        val responseData = response.body()

                        Log.i("TMDB", "onResponse: ${Gson().toJson(responseData)}")
                        var titleMovieString = ""
                        responseData?.results?.forEach {
                            titleMovieString += it.title +"\n"
                        }

                        textResponseView.text = titleMovieString
                    }

                })
    }
}