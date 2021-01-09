package com.sergeybukarev.moviestestapp.presentation.helpers

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class ArgumentsDelegate<T>(private val putT: Bundle.(String, T) -> Unit, private val defaultValueFactory: (() -> T?)?, private val defaultValue: T?) : ReadWriteProperty<Fragment, T> {
    init {
        check(defaultValueFactory == null || defaultValue == null) {
            "You can't have both `defaultValueFactory` and `defaultValue` at the same time"
        }
    }

    object Unset

    private var value: Any? = Unset

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (value == Unset) {
            value = thisRef.arguments?.get(argumentName(property)) as? T
            val defaultValue = defaultValueFactory?.invoke() ?: defaultValue
            if (value == null && defaultValue != null) {
                setValue(thisRef, property, defaultValue)
            }
        }
        return value as T
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        ensureArguments(thisRef)
        this.value = value
        thisRef.requireArguments().putT(argumentName(property), value)
    }

    private fun ensureArguments(fragment: Fragment) {
        if (fragment.arguments == null) {
            fragment.arguments = Bundle()
        }
    }

    private fun argumentName(property: KProperty<*>) = "args.${property.name}"
}

object FragmentArgumentsDelegates {
    fun <T : ArrayList<String>> stringArrayList(defaultValue: T? = null): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putStringArrayList, null, defaultValue)

    fun <T : String?> string(defaultValue: T? = null): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putString, null, defaultValue)

    fun <T : String?> string(defaultValue: () -> T?): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putString, defaultValue, null)

    fun <T : CharSequence?> charSequence(defaultValue: T? = null): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putCharSequence, null, defaultValue)

    fun <T : CharSequence?> charSequence(defaultValue: () -> T?): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putCharSequence, defaultValue, null)

    fun <T : Parcelable?> parcelable(defaultValue: T? = null): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putParcelable, null, defaultValue)

    fun <T : Parcelable?> parcelable(defaultValue: () -> T?): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putParcelable, defaultValue, null)

    fun boolean(defaultValue: Boolean = false): ReadWriteProperty<Fragment, Boolean> =
        ArgumentsDelegate(Bundle::putBoolean, null, defaultValue)

    fun boolean(defaultValue: () -> Boolean): ReadWriteProperty<Fragment, Boolean> =
        ArgumentsDelegate(Bundle::putBoolean, defaultValue, null)

    fun int(defaultValue: Int = 0): ReadWriteProperty<Fragment, Int> =
        ArgumentsDelegate(Bundle::putInt, null, defaultValue)

    fun int(defaultValue: () -> Int): ReadWriteProperty<Fragment, Int> =
        ArgumentsDelegate(Bundle::putInt, defaultValue, null)

    fun long(defaultValue: Long = 0L): ReadWriteProperty<Fragment, Long> =
        ArgumentsDelegate(Bundle::putLong, null, defaultValue)

    fun long(defaultValue: () -> Long): ReadWriteProperty<Fragment, Long> =
        ArgumentsDelegate(Bundle::putLong, defaultValue, null)
}

@Suppress("unused")
val Fragment.args
    get() = FragmentArgumentsDelegates
