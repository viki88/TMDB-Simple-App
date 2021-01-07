package com.purwadhika.simplemovieappthemoviedb.ui.favorite

import com.purwadhika.simplemovieappthemoviedb.database.table.FavMovie

interface OnClickFavoriteMovieListener {
    fun onClicked(favMovie: FavMovie)
    fun onLongClicked(favMovie: FavMovie)
}