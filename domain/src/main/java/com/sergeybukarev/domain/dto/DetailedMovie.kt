package com.sergeybukarev.domain.dto

data class DetailedMovie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val status: String,
    val popularity: Double,
    val vote_average: Double,
    val genres: List<Genre>,
)
