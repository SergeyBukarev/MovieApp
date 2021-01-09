package com.sergeybukarev.apiclient.dto.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val popularity: Double,
    val vote_average: Double,
    val genres: List<MovieGenreResponseItem>,
)
