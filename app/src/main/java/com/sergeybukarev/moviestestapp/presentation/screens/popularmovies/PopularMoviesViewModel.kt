package com.sergeybukarev.moviestestapp.presentation.screens.popularmovies

import com.jakewharton.rxrelay3.BehaviorRelay
import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.domain.errors.NoMoreItemsException
import com.sergeybukarev.domain.interactors.FetchMovieInteractor
import com.sergeybukarev.domain.interactors.ObserveMovieInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.deejdev.rxaction3.Action
import toothpick.InjectConstructor

@InjectConstructor
class PopularMoviesViewModel(
    private val fetchInteractor: FetchMovieInteractor,
    private val observeInteractor: ObserveMovieInteractor,
) {
    private val _hasMoreToLoad: BehaviorRelay<Boolean> = BehaviorRelay.createDefault(true)
    private val hasMoreToLoad: Observable<Boolean> = _hasMoreToLoad

    val items: Observable<List<ShortMoviePage>>
        get() = observeInteractor.popular()
            .observeOn(AndroidSchedulers.mainThread())

    val fetchNextPageAction: Action<Unit, Nothing> = Action.fromCompletable(AndroidSchedulers.mainThread(), isUserEnabled = hasMoreToLoad) {
        fetchInteractor.fetchMoreMovies()
            .handleNoMoreItems()
    }

    private fun Completable.handleNoMoreItems(): Completable {
        return this.onErrorResumeNext {
            if (it is NoMoreItemsException) {
                _hasMoreToLoad.accept(false)
                return@onErrorResumeNext Completable.complete()
            } else {
                return@onErrorResumeNext Completable.error(it)
            }
        }
    }
}
