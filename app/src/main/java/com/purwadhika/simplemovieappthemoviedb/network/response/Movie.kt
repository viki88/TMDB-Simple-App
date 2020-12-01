package com.purwadhika.simplemovieappthemoviedb.network.response

data class Movie(
        var video :Boolean,
        var id :Long,
        var overview :String,
        var release_date :String,
        var adult :Boolean,
        var backdrop_path :String,
        var vote_count :Int,
        var genre_ids :List<Int>,
        var vote_average :Double,
        var original_language :String,
        var original_title :String,
        var poster_path :String,
        var title :String,
        var popularity :Double,
        var media_type :String
)