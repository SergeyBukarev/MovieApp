package com.sergeybukarev.domain.dto

data class ShortMoviePage(
    val page: Int,
    val movies: List<ShortMovie>,
    val totalPages: Int,
    val totalResults: Int,
)
