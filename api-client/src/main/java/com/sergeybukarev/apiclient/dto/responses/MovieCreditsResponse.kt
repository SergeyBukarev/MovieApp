package com.sergeybukarev.apiclient.dto.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieCreditsResponse(
    val id: Int,
    val cast: List<MovieCastResponseItem>,
)
