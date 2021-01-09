package com.sergeybukarev.apiclient.dto.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMovieResponseItem(
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val backdrop_path: String?,
    val poster_path: String?,
    val popularity: Double,
    val vote_average: Double,
    val release_date: String?,
)
