package com.sergeybukarev.domain.interactors

import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMovie
import com.sergeybukarev.domain.gateways.MovieGateway
import io.reactivex.rxjava3.core.Single

class MovieInteractor(
    private val movieGateway: MovieGateway,
) {

    fun getPopularMovie(page: Int): Single<List<ShortMovie>> {
        return movieGateway.getPopularMovie(page)
    }

    fun getDetailMovie(movieId: Int): Single<DetailedMovie> {
        return movieGateway.getDetailMovie(movieId)
    }

    fun getCasts(movieId: Int): Single<List<Cast>> {
        return movieGateway.getCasts(movieId)
    }

}
