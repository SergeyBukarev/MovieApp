package com.sergeybukarev.moviestestapp.navigation.coordinators.base

import android.os.Bundle
import androidx.annotation.AnyThread
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.Navigator
import javax.inject.Provider

abstract class BaseCoordinator(
    protected val lifecycle: Lifecycle,
) {
    @AnyThread
    fun Provider<NavController>.setGraphWhenCreated(@NavigationRes graphResId: Int, startDestinationArgs: Bundle?) {
        lifecycle.coroutineScope.launchWhenCreated {
            get().setGraph(graphResId, startDestinationArgs)
        }
    }

    @AnyThread
    fun Provider<NavController>.navigateWhenStarted(@IdRes resId: Int, args: Bundle?, extras: Navigator.Extras? = null) {
        lifecycle.coroutineScope.launchWhenStarted {
            get().navigate(resId, args, null, extras)
        }
    }
}
