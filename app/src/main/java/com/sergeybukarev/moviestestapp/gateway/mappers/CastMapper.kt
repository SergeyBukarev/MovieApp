package com.sergeybukarev.moviestestapp.gateway.mappers

import android.net.Uri
import com.sergeybukarev.apiclient.dto.responses.MovieCastResponseItem
import com.sergeybukarev.domain.dto.Cast
import toothpick.InjectConstructor

@InjectConstructor
class CastMapper {

    fun map(from: MovieCastResponseItem): Cast {
        return Cast(
            from.id,
            from.name,
            from.original_name,
            mapUri(from.profile_path),
            from.character,
            from.order,
        )
    }

    private fun mapUri(url: String?): Uri? {
        return if (url == null) {
            null
        } else {
            val baseUrl = "https://image.tmdb.org/t/p/original/"
            Uri.parse(baseUrl.plus(url))
        }
    }
}
