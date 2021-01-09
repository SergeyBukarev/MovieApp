package com.sergeybukarev.moviestestapp.core.toothpick.modules

import androidx.navigation.NavController
import com.sergeybukarev.moviestestapp.core.toothpick.providers.NavControllerHolder
import toothpick.config.Module
import toothpick.ktp.binding.bind
import kotlin.reflect.KClass

class NavControllerModule(qualifier: KClass<out Annotation>) : Module() {
    init {
        val holder = NavControllerHolder()
        bind<NavController>().withName(qualifier).toProviderInstance(holder)
        bind<NavControllerHolder>().withName(qualifier).toInstance(holder)
    }
}
