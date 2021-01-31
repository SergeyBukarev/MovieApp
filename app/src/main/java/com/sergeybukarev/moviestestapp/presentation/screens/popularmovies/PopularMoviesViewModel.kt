package com.sergeybukarev.moviestestapp.presentation.screens.popularmovies

import com.jakewharton.rxrelay3.BehaviorRelay
import com.sergeybukarev.domain.dto.ShortMovie
import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.domain.interactors.FetchMovieInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.deejdev.rxaction3.Action
import org.deejdev.rxaction3.invoke
import toothpick.InjectConstructor

@InjectConstructor
class PopularMoviesViewModel(fetchMovieInteractor: FetchMovieInteractor) {
    private val initialPage: Int = 1
    private val fetchThreshold: Int = 5

    private val _items: BehaviorRelay<List<ShortMovie>> = BehaviorRelay.createDefault(emptyList())
    val items: Observable<List<ShortMovie>> get() = _items.observeOn(AndroidSchedulers.mainThread())

    private var currentPage: BehaviorRelay<Int> = BehaviorRelay.createDefault(initialPage)
    private var maxPages: BehaviorRelay<Int> = BehaviorRelay.createDefault(initialPage)
    private val _hasMoreToLoad: BehaviorRelay<Boolean> = BehaviorRelay.createDefault(true)
    private val hasMoreToLoad: Observable<Boolean> = _hasMoreToLoad

    fun prefetchIfNeeded(currentItemIndex: Int) {
        if ((currentItemIndex + fetchThreshold) > _items.value.lastIndex) {
            if (currentPage.value <= maxPages.value) {
                loadMoreAction()
            }
        }
    }

    val loadMoreAction: Action<Unit, Nothing> = Action.fromCompletable(AndroidSchedulers.mainThread(), isUserEnabled = hasMoreToLoad) {
        fetchMovieInteractor.fetchMovies(currentPage.value)
            .doOnSuccess(::handleNewItems)
            .ignoreElement()
    }

    private fun handleNewItems(page: ShortMoviePage) {
        if (page.movies.isNotEmpty()) {
            _hasMoreToLoad.accept(true)
            _items.accept(_items.value!! + page.movies)
            maxPages.accept(page.totalPages)
            currentPage.accept(currentPage.value + 1)
        } else {
            _hasMoreToLoad.accept(false)
        }
    }
}
