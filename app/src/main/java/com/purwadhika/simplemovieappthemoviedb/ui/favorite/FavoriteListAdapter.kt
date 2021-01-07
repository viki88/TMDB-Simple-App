package com.purwadhika.simplemovieappthemoviedb.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.purwadhika.simplemovieappthemoviedb.Constant
import com.purwadhika.simplemovieappthemoviedb.database.table.FavMovie
import com.purwadhika.simplemovieappthemoviedb.databinding.RowTrendingMovieLayoutBinding
import com.squareup.picasso.Picasso

class FavoriteListAdapter(var onClickFavoriteMovieListener: OnClickFavoriteMovieListener) : RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder>(){

    var listFavMovie = listOf<FavMovie>()

    inner class FavoriteListViewHolder(var binding :RowTrendingMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(favMovie: FavMovie){
            Picasso.get().load(Constant.IMAGE_BASE_URL + Constant.IMAGE_SIZE_500_SIZE +favMovie.imageUrl).into(binding.previewMovie)

            binding.titleMovie.text = favMovie.title
            binding.ratingMovie.visibility = View.GONE

            itemView.setOnClickListener {
                onClickFavoriteMovieListener.onClicked(favMovie)
            }

            itemView.setOnLongClickListener {
                onClickFavoriteMovieListener.onLongClicked(favMovie)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        val binding = RowTrendingMovieLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return FavoriteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bind(listFavMovie[position])
    }

    override fun getItemCount(): Int = listFavMovie.size

    fun updateList(listFavMovie :List<FavMovie>){
        this.listFavMovie = listFavMovie
        notifyDataSetChanged()
    }
}