package com.sergeybukarev.moviestestapp.core.toothpick.providers

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import javax.inject.Provider

open class BaseLifecycleAwareHolder<T> : Provider<T> {
    private var current: T? = null

    override fun get(): T = current!!

    fun setCurrent(value: T, lifecycle: Lifecycle) {
        current = value

        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                if (current === value) {
                    current = null
                }
            }
        })
    }
}

fun <T> BaseLifecycleAwareHolder<T>.setCurrent(value: T) where T : LifecycleOwner {
    setCurrent(value, value.lifecycle)
}
