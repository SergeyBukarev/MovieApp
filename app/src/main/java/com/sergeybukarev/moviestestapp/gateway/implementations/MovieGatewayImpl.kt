package com.sergeybukarev.moviestestapp.gateway.implementations

import com.sergeybukarev.apiclient.services.MovieApiService
import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMoviePage
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

    override fun getPopularMovies(page: Int): Single<ShortMoviePage> {
        return movieApiService.getPopularMovies(page).map { ShortMoviePage(it.page, it.results.map(movieMapper::mapPopular), it.total_pages, it.total_results) }
    }

    override fun getMovieDetails(movieId: Int): Single<DetailedMovie> {
        return movieApiService.getMovieDetails(movieId).map(movieMapper::mapDetail)
    }

    override fun getCasts(movieId: Int): Single<List<Cast>> {
        return movieApiService.getMovieCredits(movieId).map { it.cast.map(castMapper::map) }
    }
}
