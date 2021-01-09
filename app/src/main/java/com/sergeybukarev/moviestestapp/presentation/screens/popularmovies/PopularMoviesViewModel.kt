package com.sergeybukarev.moviestestapp.presentation.screens.popularmovies

import com.jakewharton.rxrelay3.BehaviorRelay
import com.sergeybukarev.domain.dto.ShortMovie
import com.sergeybukarev.domain.interactors.MovieInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.deejdev.rxaction3.Action
import org.deejdev.rxaction3.invoke
import toothpick.InjectConstructor

@InjectConstructor
class PopularMoviesViewModel(movieInteractor: MovieInteractor) {
    private val initialPage: Int = 1
    private val fetchThreshold: Int = 5
    private var currentPage: Int = initialPage
    private var lastFetchItemIndex: Int? = null

    private val _items: BehaviorRelay<List<ShortMovie>> = BehaviorRelay.createDefault(emptyList())
    val items: Observable<List<ShortMovie>> get() = _items.observeOn(AndroidSchedulers.mainThread())

    private val _hasMoreToLoad: BehaviorRelay<Boolean> = BehaviorRelay.createDefault(true)
    private val hasMoreToLoad: Observable<Boolean> = _hasMoreToLoad

    fun prefetchIfNeeded(currentItemIndex: Int) {
        if (lastFetchItemIndex == null || (lastFetchItemIndex != null && currentItemIndex > lastFetchItemIndex!!)) {
            if ((currentItemIndex + fetchThreshold) > _items.value.size - 1) {
                lastFetchItemIndex = currentItemIndex
                loadMoreAction()
            }
        }
    }

    val loadMoreAction: Action<Unit, Nothing> = Action.fromCompletable(isUserEnabled = hasMoreToLoad) {
        movieInteractor.getPopularMovie(currentPage)
            .doOnSuccess(::handleNewItems)
            .ignoreElement()
    }

    private fun handleNewItems(items: List<ShortMovie>) {
        if (items.isNotEmpty()) {
            _hasMoreToLoad.accept(true)
            _items.accept(_items.value!! + items)
            currentPage += 1
        } else {
            _hasMoreToLoad.accept(false)
        }
    }
}
