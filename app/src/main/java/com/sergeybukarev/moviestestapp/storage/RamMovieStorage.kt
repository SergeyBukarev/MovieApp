package com.sergeybukarev.moviestestapp.storage

import com.jakewharton.rxrelay3.BehaviorRelay
import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.moviestestapp.core.toothpick.scopes.RootScope
import toothpick.InjectConstructor
import javax.inject.Singleton

@Singleton
@RootScope
@InjectConstructor
class RamMovieStorage {
    val moviesFeedItems: BehaviorRelay<List<ShortMoviePage>> = BehaviorRelay.createDefault(emptyList())
}
