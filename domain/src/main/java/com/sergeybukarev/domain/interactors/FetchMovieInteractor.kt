package com.sergeybukarev.domain.interactors

import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.domain.gateways.MovieGateway
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
class FetchMovieInteractor(
    private val movieGateway: MovieGateway,
) {
    fun fetchMovies(page: Int): Single<ShortMoviePage> {
        return movieGateway.getPopularMovies(page)
    }
}
