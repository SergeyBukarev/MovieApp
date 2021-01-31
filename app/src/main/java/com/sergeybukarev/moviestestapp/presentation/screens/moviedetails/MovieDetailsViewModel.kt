package com.sergeybukarev.moviestestapp.presentation.screens.moviedetails

import com.jakewharton.rxrelay3.BehaviorRelay
import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.interactors.MovieDetailsInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.deejdev.rxaction3.Action
import toothpick.InjectConstructor

@InjectConstructor
class MovieDetailsViewModel(
    movieDetailsInteractor: MovieDetailsInteractor,
) {
    private val _castItems: BehaviorRelay<List<Cast>> = BehaviorRelay.createDefault(emptyList())
    val castItems: Observable<List<Cast>> get() = _castItems.observeOn(AndroidSchedulers.mainThread())

    val loadCastAction: Action<Unit, Nothing> = Action.fromCompletable(AndroidSchedulers.mainThread()) {
        movieDetailsInteractor.getCasts()
            .doOnSuccess(::handleCastItems)
            .ignoreElement()
    }

    val loadDetailsAction: Action<Unit, DetailedMovie> = Action.fromSingle(AndroidSchedulers.mainThread()) {
        movieDetailsInteractor.getMovieDetails()
    }

    private fun handleCastItems(items: List<Cast>) {
        if (items.isNotEmpty()) {
            _castItems.accept(items)
        }
    }
}
