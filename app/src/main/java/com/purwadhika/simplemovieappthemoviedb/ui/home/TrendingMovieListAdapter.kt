package com.purwadhika.simplemovieappthemoviedb.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.purwadhika.simplemovieappthemoviedb.Constant
import com.purwadhika.simplemovieappthemoviedb.R
import com.purwadhika.simplemovieappthemoviedb.network.response.Genre
import com.purwadhika.simplemovieappthemoviedb.network.response.Movie
import com.squareup.picasso.Picasso

class TrendingMovieListAdapter(private var context: Context,private var onClickMovieListListerner: OnClickMovieListListerner) :
        RecyclerView.Adapter<TrendingMovieListAdapter.TrendingMovieViewHolder>(){

    private var listMovie = listOf<Movie>()
    private var genreList = listOf<Genre>()

    inner class TrendingMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imagePreviewView = itemView.findViewById<ImageView>(R.id.preview_movie)
        private var titleMovieView = itemView.findViewById<TextView>(R.id.title_movie)
        private var ratingView = itemView.findViewById<TextView>(R.id.rating_movie)

        fun bind(movie :Movie){
            // load image into ImageView
            Picasso.get().load(Constant.IMAGE_BASE_URL + Constant.IMAGE_SIZE_500_SIZE +movie.poster_path).into(imagePreviewView)

            titleMovieView.text = movie.title

            val ratingString = "Rating : ${movie.vote_average}"
            val genreStringArray = arrayListOf<String>()
            movie.genre_ids.forEach { genreid ->
                val genreName = genreList.find { genre -> genre.id == genreid }?.name
                genreName?.let { genreStringArray.add(it)}
            }

            ratingView.text = genreStringArray.joinToString(separator = ",") + "\n" + ratingString

            itemView.setOnClickListener {
                onClickMovieListListerner.onClickMovieList(movie)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_trending_movie_layout, null)
        return TrendingMovieViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    fun updateList(listMovie :List<Movie>){
        this.listMovie = listMovie
        notifyDataSetChanged()
    }

    fun updateGenreList(genreList :List<Genre>){
        this.genreList = genreList
    }

    interface OnClickMovieListListerner{
        fun onClickMovieList(movie: Movie)
    }
}