package com.purwadhika.simplemovieappthemoviedb.network.response

data class TrendingMovieResponse(
        var page :Int,
        var results :List<Movie>
)