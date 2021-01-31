package com.sergeybukarev.domain.interactors

import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.domain.gateways.MovieGateway
import io.reactivex.rxjava3.core.Observable
import toothpick.InjectConstructor

@InjectConstructor
class ObserveMovieInteractor(
    private val gateway: MovieGateway
) {
    fun popular(): Observable<List<ShortMoviePage>> {
        return gateway.observeRamPopular()
    }
}
