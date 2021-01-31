package com.sergeybukarev.apiclient.dto.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    val page: Int,
    val results: List<PopularMovieResponseItem>?,
    val total_pages: Int,
    val total_results: Int,
)
