package com.purwadhika.simplemovieappthemoviedb.ui.detailfavmovie

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.purwadhika.simplemovieappthemoviedb.Constant
import com.purwadhika.simplemovieappthemoviedb.database.table.FavMovie
import com.purwadhika.simplemovieappthemoviedb.databinding.ActivityDetailFavMovieBinding
import com.squareup.picasso.Picasso

class DetailFavMovieActivity : AppCompatActivity() {

    lateinit var binding :ActivityDetailFavMovieBinding
    var favMovie :FavMovie? = null

    companion object{
        const val FAV_DATA = "fav_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFavMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favMovie = intent.getParcelableExtra(FAV_DATA)

        setupToolbar()
        setupData()
    }

    private fun setupData(){
        favMovie?.let {
            supportActionBar?.title = it.title

            Picasso.get().load(Constant.IMAGE_BASE_URL+ Constant.IMAGE_SIZE_500_SIZE+it.imageUrl).into(binding.imageDetail)
            binding.detailText.text = it.desc
        }
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbarDetailMovie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}