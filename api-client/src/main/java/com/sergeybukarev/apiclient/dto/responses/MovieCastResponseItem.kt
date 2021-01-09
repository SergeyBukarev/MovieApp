package com.sergeybukarev.apiclient.dto.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCastResponseItem(
    val id: Int,
    val name: String,
    val original_name: String,
    val profile_path: String?,
    val character: String,
    val order: Int,
)
