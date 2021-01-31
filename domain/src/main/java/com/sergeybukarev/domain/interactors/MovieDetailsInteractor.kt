package com.sergeybukarev.domain.interactors

import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.domain.gateways.MovieGateway
import com.sergeybukarev.domain.toothpick.helpers.BoxedInt
import com.sergeybukarev.domain.toothpick.qualifiers.MovieId
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
class MovieDetailsInteractor(
    @MovieId private val movieId: BoxedInt,
    private val movieGateway: MovieGateway,
) {
    fun getMovieDetails(): Single<DetailedMovie> {
        return movieGateway.getMovieDetails(movieId as Int)
    }

    fun getCasts(): Single<List<Cast>> {
        return movieGateway.getCasts(movieId as Int)
    }
}
