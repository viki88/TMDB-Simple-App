package com.purwadhika.simplemovieappthemoviedb.ui.detailmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.purwadhika.simplemovieappthemoviedb.Constant
import com.purwadhika.simplemovieappthemoviedb.R
import com.purwadhika.simplemovieappthemoviedb.database.table.FavMovie
import com.purwadhika.simplemovieappthemoviedb.databinding.ActivityDetailMovieBinding
import com.purwadhika.simplemovieappthemoviedb.network.response.Movie
import com.squareup.picasso.Picasso

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding :ActivityDetailMovieBinding
    private var movie : Movie? = null
    private var isFavMovie = false
    private val viewModel :DetailMovieViewModel by viewModels()
    var menu: Menu? = null

    companion object{
        const val MOVIE_DATA = "movie_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.favMovieLiveData.observe(this){
            Log.i("movie", "onCreate: $it")
            isFavMovie = it.find { movieOnDb -> movieOnDb.idmovie == movie?.id } != null
            menu?.findItem(R.id.menu_fav)?.setIcon(if (isFavMovie) R.drawable.ic_fav_enable else R.drawable.ic_fav_disable)
        }

        viewModel.statusProgressDBLiveData.observe(this){
            when(it){
                DetailMovieViewModel.StatusProgressDb.SAVE_SUCCESS -> {
                    Toast.makeText(this@DetailMovieActivity, "Saved to Favorite database", Toast.LENGTH_SHORT).show()
                }
                DetailMovieViewModel.StatusProgressDb.DELETE_SUCCESS ->{
                    Toast.makeText(this@DetailMovieActivity, "Deleted from Favorite database", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setupToolbar()

        movie = intent.getParcelableExtra(MOVIE_DATA)

        setupData()

        viewModel.loadAllFavMovie()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbarDetailMovie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupData(){
        movie?.let {
            supportActionBar?.title = it.title

            Picasso.get().load(Constant.IMAGE_BASE_URL+Constant.IMAGE_SIZE_500_SIZE+it.poster_path).into(binding.imageDetail)
            binding.detailText.text = it.overview
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                false
            }
            R.id.menu_fav -> {
                if (!isFavMovie) savetoFavList() else deleteFromFavList()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_movie, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    private fun savetoFavList(){
        movie?.let {
            viewModel.savFavMovie(
                    FavMovie(
                            idmovie = it.id,
                            title = it.title,
                            imageUrl = it.poster_path,
                            desc = it.overview
                    ))
        }
    }

    private fun deleteFromFavList(){
        movie?.let { viewModel.deleteFavMovie(it.id) }
    }
}