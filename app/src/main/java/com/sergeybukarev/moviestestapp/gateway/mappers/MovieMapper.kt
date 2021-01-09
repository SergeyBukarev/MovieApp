package com.sergeybukarev.moviestestapp.gateway.mappers

import android.net.Uri
import com.sergeybukarev.apiclient.dto.responses.MovieDetailsResponse
import com.sergeybukarev.apiclient.dto.responses.MovieGenreResponseItem
import com.sergeybukarev.apiclient.dto.responses.PopularMovieResponseItem
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.Genre
import com.sergeybukarev.domain.dto.ShortMovie
import toothpick.InjectConstructor

@InjectConstructor
class MovieMapper {
    fun mapDetail(from: MovieDetailsResponse): DetailedMovie {
        return DetailedMovie(
            from.id,
            from.title,
            from.original_title,
            from.overview,
            mapUri(from.poster_path)!!,
            from.release_date,
            from.runtime,
            from.status,
            from.popularity,
            from.vote_average,
            from.genres.map(::mapGenre),
        )
    }

    fun mapPopular(from: PopularMovieResponseItem): ShortMovie {
        return ShortMovie(
            from.id,
            from.title,
            from.original_title,
            from.overview,
            mapUri(from.backdrop_path),
            mapUri(from.poster_path),
            from.popularity,
            from.vote_average,
            from.release_date,
        )
    }

    private fun mapGenre(from: MovieGenreResponseItem): Genre {
        return Genre(
            from.id,
            from.name,
        )
    }

    private fun mapUri(url: String): Uri {
        val baseUrl = "https://image.tmdb.org/t/p/original/"
        return Uri.parse(baseUrl.plus(url))
    }
}
