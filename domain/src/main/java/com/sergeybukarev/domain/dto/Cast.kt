package com.sergeybukarev.domain.dto

import android.net.Uri

data class Cast(
    val id: Int,
    val name: String,
    val originalName: String,
    val avatar: Uri?,
    val character: String,
    val order: Int,
)
