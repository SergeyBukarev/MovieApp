package com.sergeybukarev.moviestestapp.core.toothpick.modules

import com.sergeybukarev.moviestestapp.core.platform.MainActivity
import com.sergeybukarev.moviestestapp.navigation.coordinators.RootCoordinator
import com.sergeybukarev.moviestestapp.presentation.screens.popularmovies.PopularMoviesFragment
import toothpick.Scope
import toothpick.config.Module
import toothpick.ktp.binding.bind

class RootNavigationModule(scope: Scope) : Module() {
    init {
        val rootCoordinatorProvider = { scope.getInstance(RootCoordinator::class.java) }
        bind<RootCoordinator>().singleton()
        bind<MainActivity.Delegate>().toProviderInstance(rootCoordinatorProvider)
        bind<PopularMoviesFragment.Delegate>().toProviderInstance(rootCoordinatorProvider)
    }
}

