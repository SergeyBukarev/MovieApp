package com.sergeybukarev.moviestestapp.core.toothpick.modules

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.sergeybukarev.moviestestapp.core.toothpick.providers.ActivityHolder
import com.sergeybukarev.moviestestapp.core.toothpick.qualifiers.ActivityLogicalLifecycle
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ActivityModule(logicalLifecycle: Lifecycle) : Module() {
    init {
        bind<Lifecycle>().withName(ActivityLogicalLifecycle::class).toInstance(logicalLifecycle)
    }

    init {
        val holder = ActivityHolder()
        bind<AppCompatActivity>().toProviderInstance(holder)
        bind<ActivityHolder>().toInstance(holder)
    }
}
