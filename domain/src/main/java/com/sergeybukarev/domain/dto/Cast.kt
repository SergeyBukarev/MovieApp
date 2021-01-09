package com.sergeybukarev.domain.dto

data class Cast(
    val id: Int,
    val name: String,
    val originalName: String,
    val profilePath: String,
    val character: String,
    val order: Int,
)
