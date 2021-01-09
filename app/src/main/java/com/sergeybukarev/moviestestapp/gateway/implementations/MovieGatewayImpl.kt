package com.sergeybukarev.moviestestapp.gateway.implementations

import com.sergeybukarev.apiclient.services.MovieApiService
import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMovie
import com.sergeybukarev.domain.gateways.MovieGateway
import com.sergeybukarev.moviestestapp.gateway.mappers.CastMapper
import com.sergeybukarev.moviestestapp.gateway.mappers.MovieMapper
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
class MovieGatewayImpl(
    private val movieApiService: MovieApiService,
    private val movieMapper: MovieMapper,
    private val castMapper: CastMapper,
) : MovieGateway {

    override fun getPopularMovie(page: Int): Single<List<ShortMovie>> {
        return movieApiService.getPopularMovies(page).map { it.results.map(movieMapper::mapPopular) }
    }

    override fun getDetailMovie(movieId: Int): Single<DetailedMovie> {
        return movieApiService.getMovieDetails(movieId).map(movieMapper::mapDetail)
    }

    override fun getCasts(movieId: Int): Single<List<Cast>> {
        return movieApiService.getMovieCredits(movieId).map { it.cast.map(castMapper::map) }
    }
}
