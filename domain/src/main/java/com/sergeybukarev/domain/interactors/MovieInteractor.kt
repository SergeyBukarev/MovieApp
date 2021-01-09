package com.sergeybukarev.domain.interactors

import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.domain.gateways.MovieGateway
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
class MovieInteractor(
    private val movieGateway: MovieGateway,
) {

    fun getPopularMovies(page: Int): Single<ShortMoviePage> {
        return movieGateway.getPopularMovies(page)
    }

    fun getMovieDetails(movieId: Int): Single<DetailedMovie> {
        return movieGateway.getMovieDetails(movieId)
    }

    fun getCasts(movieId: Int): Single<List<Cast>> {
        return movieGateway.getCasts(movieId)
    }

}
