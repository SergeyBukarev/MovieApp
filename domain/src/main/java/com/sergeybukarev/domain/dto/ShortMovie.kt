package com.sergeybukarev.domain.dto

import android.net.Uri

data class ShortMovie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val backdropImage: Uri,
    val posterImage: Uri,
    val popularity: Double,
    val voteAverage: Double,
    val releaseDate: String,
)
