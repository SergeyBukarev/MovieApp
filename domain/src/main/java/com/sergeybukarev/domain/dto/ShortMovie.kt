package com.sergeybukarev.domain.dto

data class ShortMovie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val popularity: Double,
    val voteAverage: Double,
    val releaseDate: String,
)
