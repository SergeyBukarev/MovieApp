package com.sergeybukarev.moviestestapp.gateway.implementations

import com.sergeybukarev.apiclient.services.MovieApiService
import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.domain.errors.NoMoreItemsException
import com.sergeybukarev.domain.gateways.MovieGateway
import com.sergeybukarev.moviestestapp.gateway.mappers.CastMapper
import com.sergeybukarev.moviestestapp.gateway.mappers.MovieMapper
import com.sergeybukarev.moviestestapp.storage.RamMovieStorage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
class MovieGatewayImpl(
    private val movieApiService: MovieApiService,
    private val movieMapper: MovieMapper,
    private val castMapper: CastMapper,
    private val storage: RamMovieStorage,
) : MovieGateway {

    override fun fetchPopular(page: Int): Single<ShortMoviePage> {
        return movieApiService.getPopularMovies(page)
            .map { ShortMoviePage(it.page, it.results?.map(movieMapper::mapPopular) ?: emptyList(), it.total_pages, it.total_results) }
            .flatMap {
                if (it.movies.isNotEmpty()) {
                    return@flatMap Single.just(it)
                } else {
                    return@flatMap Single.error(NoMoreItemsException())
                }
            }
    }

    override fun appendPopularInRam(item: ShortMoviePage): Completable {
        return Completable.fromAction { storage.moviesFeedItems.accept(storage.moviesFeedItems.value + item) }
    }

    override fun observeRamPopular(): Observable<List<ShortMoviePage>> = storage.moviesFeedItems

    override fun getLastPage(): Int? {
        return storage.moviesFeedItems.value.lastOrNull()?.page
    }


    override fun getMovieDetails(movieId: Int): Single<DetailedMovie> {
        return movieApiService.getMovieDetails(movieId).map(movieMapper::mapDetail)
    }

    override fun getCasts(movieId: Int): Single<List<Cast>> {
        return movieApiService.getMovieCredits(movieId).map { it.cast.map(castMapper::map) }
    }
}
