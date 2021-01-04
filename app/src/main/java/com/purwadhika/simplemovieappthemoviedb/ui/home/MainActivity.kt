package com.purwadhika.simplemovieappthemoviedb.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.purwadhika.simplemovieappthemoviedb.Constant
import com.purwadhika.simplemovieappthemoviedb.R
import com.purwadhika.simplemovieappthemoviedb.network.ApiService
import com.purwadhika.simplemovieappthemoviedb.network.response.Genre
import com.purwadhika.simplemovieappthemoviedb.network.response.GenreResponse
import com.purwadhika.simplemovieappthemoviedb.network.response.Movie
import com.purwadhika.simplemovieappthemoviedb.network.response.TrendingMovieResponse
import com.purwadhika.simplemovieappthemoviedb.ui.detailmovie.DetailMovieActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), TrendingMovieListAdapter.OnClickMovieListListerner {

    lateinit var progressBarView :ProgressBar
    lateinit var trendingRecyclerView :RecyclerView
    lateinit var adapter: TrendingMovieListAdapter
    var genreList = listOf<Genre>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBarView = findViewById(R.id.progress_bar)
        trendingRecyclerView = findViewById(R.id.rv_trending_list)

        setupListView()

        loadGenreList()
    }

    private fun setupListView(){
        adapter = TrendingMovieListAdapter(this, this)
        trendingRecyclerView.layoutManager = LinearLayoutManager(this)
        trendingRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        trendingRecyclerView.adapter = adapter
    }

    private fun loadGenreList(){
        progressBarView.visibility = View.VISIBLE
        ApiService().getTheMovieDbApiService().getAllGenres(Constant.API_KEY)
            .enqueue(object :Callback<GenreResponse>{
                override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                    progressBarView.visibility = View.GONE
                    Log.i("TMDB", "onFailure: ${t.message}")
                    Toast.makeText(this@MainActivity, "error response : ${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<GenreResponse>,
                    response: Response<GenreResponse>
                ) {
                    progressBarView.visibility = View.GONE
                    val responseData = response.body()
                    responseData?.let {
                        genreList = it.genres
                        loadTrendingMovie()
                    }
                }

            })
    }
    
    private fun loadTrendingMovie(){
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

                        responseData?.let {
                            adapter.updateGenreList(genreList)
                            adapter.updateList(it.results)
                        }
                    }

                })
    }

    override fun onClickMovieList(movie: Movie) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_DATA, movie)
        startActivity(intent)
    }
}