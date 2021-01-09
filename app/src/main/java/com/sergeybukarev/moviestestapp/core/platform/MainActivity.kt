package com.sergeybukarev.moviestestapp.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import com.sergeybukarev.moviestestapp.R
import com.sergeybukarev.moviestestapp.core.toothpick.modules.ActivityModule
import com.sergeybukarev.moviestestapp.core.toothpick.modules.ApiModule
import com.sergeybukarev.moviestestapp.core.toothpick.modules.NavControllerModule
import com.sergeybukarev.moviestestapp.core.toothpick.modules.RootNavigationModule
import com.sergeybukarev.moviestestapp.core.toothpick.providers.NavControllerHolder
import com.sergeybukarev.moviestestapp.core.toothpick.qualifiers.RootNavigation
import com.sergeybukarev.moviestestapp.core.toothpick.scopes.RootScope
import org.deejdev.scopelib.ScopeBlueprintManager
import org.deejdev.scopelib.ScopeBlueprintManagerCallbacks
import org.deejdev.scopelib.lifecycle.LogicalLifecycle
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.module.SmoothieApplicationModule

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    interface Delegate {
        fun navControllerCreated()
    }

    private val delegate by inject<Delegate>()
    private lateinit var logicalLifecycle: Lifecycle
    private lateinit var scopeBlueprintManager: ScopeBlueprintManager
    private val navControllerHolder by inject<NavControllerHolder>(RootNavigation::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        logicalLifecycle = LogicalLifecycle.register(this, savedInstanceState)
        initScopeBlueprintManager(savedInstanceState)
        initRootScope().inject(this)
        supportFragmentManager.registerFragmentLifecycleCallbacks(ScopeBlueprintManagerCallbacks(scopeBlueprintManager), true)

        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.content) as NavHostFragment
        navControllerHolder.setCurrent(navHostFragment.navController, lifecycle)
        delegate.navControllerCreated()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(ScopeBlueprintManager::class.java.name, scopeBlueprintManager)
    }


    private fun initRootScope() = KTP.openScope(RootScope::class.java) {
        it.installModules(
            SmoothieApplicationModule(application),
            ActivityModule(logicalLifecycle),
            NavControllerModule(RootNavigation::class),
            RootNavigationModule(it),
            ApiModule(),
            // TODO add other modules
        )
    }

    private fun initScopeBlueprintManager(savedInstanceState: Bundle?) {
        scopeBlueprintManager = if (savedInstanceState == null)
            ScopeBlueprintManager()
        else
            savedInstanceState.getParcelable(ScopeBlueprintManager::class.java.name)!!
    }


}
