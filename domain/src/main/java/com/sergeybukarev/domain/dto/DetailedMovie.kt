package com.sergeybukarev.domain.dto

import android.net.Uri

data class DetailedMovie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterImage: Uri?,
    val releaseDate: String,
    val runtime: Int,
    val status: String,
    val popularity: Double,
    val voteAverage: Double,
    val genres: List<Genre>,
)
