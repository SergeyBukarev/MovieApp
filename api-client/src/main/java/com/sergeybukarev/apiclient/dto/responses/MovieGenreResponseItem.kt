package com.sergeybukarev.apiclient.dto.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieGenreResponseItem(
    val id: Int,
    val name: String,
)
