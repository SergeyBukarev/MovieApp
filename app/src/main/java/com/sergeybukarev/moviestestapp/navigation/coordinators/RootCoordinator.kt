package com.sergeybukarev.moviestestapp.navigation.coordinators

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.sergeybukarev.moviestestapp.R
import com.sergeybukarev.moviestestapp.core.platform.MainActivity
import com.sergeybukarev.moviestestapp.core.toothpick.qualifiers.ActivityLogicalLifecycle
import com.sergeybukarev.moviestestapp.core.toothpick.qualifiers.RootNavigation
import com.sergeybukarev.moviestestapp.core.toothpick.scopes.RootScope
import com.sergeybukarev.moviestestapp.navigation.coordinators.base.BaseCoordinator
import org.deejdev.scopelib.useScope
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class RootCoordinator(
    @ActivityLogicalLifecycle lifecycle: Lifecycle,
    @RootNavigation private val navController: Provider<NavController>,
) : MainActivity.Delegate,
    BaseCoordinator(lifecycle) {

    // region Initialization
    override fun navControllerCreated() {
        navController.setGraphWhenCreated(R.navigation.root, Bundle().useScope<RootScope>())
    }
    // endregion
}
