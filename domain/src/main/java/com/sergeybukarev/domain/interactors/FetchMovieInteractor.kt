package com.sergeybukarev.domain.interactors

import com.sergeybukarev.domain.gateways.MovieGateway
import io.reactivex.rxjava3.core.Completable
import toothpick.InjectConstructor

@InjectConstructor
class FetchMovieInteractor(
    private val gateway: MovieGateway,
) {
    fun fetchMoreMovies(): Completable {
        // Initial page is 1
        // If last page == null therefore initial = 0 + 1 == 1
        val nextPage = (gateway.getLastPage() ?: 0).inc()
        return gateway.fetchPopular(nextPage)
            .flatMapCompletable(gateway::appendPopularInRam)
    }
}
