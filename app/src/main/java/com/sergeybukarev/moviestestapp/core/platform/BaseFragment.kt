package com.sergeybukarev.moviestestapp.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.collection.SimpleArrayMap
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import autodispose2.ScopeProvider
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.sergeybukarev.moviestestapp.R
import com.sergeybukarev.moviestestapp.presentation.helpers.args
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.deejdev.rxaction3.Action
import org.deejdev.rxaction3.invoke
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {
    protected var views: Binding? = null
    protected fun requireViews(): Binding = views!!

    protected var isColdStart: Boolean by args.boolean(true)
        private set

    protected open val isDarkStatusBar: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.javaClass.inflateViewBinding(inflater).also {
            views = it
            return it.root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        views = null
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) = super.onViewCreated(view, savedInstanceState)

    final override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        onViewCreated(requireViews(), savedInstanceState)

        val whenViewDestroyed = viewLifecycleOwner.scope(Lifecycle.Event.ON_DESTROY)
        // Замена стандартному onViewCreated, но вместо корневого view передаются все (ViewBinding),
        // а также ScopeProvider (для AutoDispose). Остальные методы служат для привязки к view model.
        // Вызывается здесь (в `onViewStateRestored`) для согласованности с `lifecycle` - он делает так же.
        onViewCreated(requireViews(), savedInstanceState, whenViewDestroyed)
        onApplyInitialModelValues(requireViews(), whenViewDestroyed)
        onBindToModel(requireViews(), whenViewDestroyed)
        onViewBound(requireViews())

        isColdStart = false
    }

    @Deprecated("Use 3-parameters version instead")
    protected open fun onViewCreated(views: Binding, savedInstanceState: Bundle?) = Unit

    @MainThread
    protected open fun onViewCreated(views: Binding, savedInstanceState: Bundle?, whenViewDestroyed: ScopeProvider) = Unit

    @MainThread
    protected open fun onApplyInitialModelValues(views: Binding, whenViewDestroyed: ScopeProvider) = Unit

    @MainThread
    protected open fun onBindToModel(views: Binding, whenViewDestroyed: ScopeProvider) = Unit

    @MainThread
    protected open fun onViewBound(views: Binding) = Unit

    @MainThread
    protected fun View.bindClicksToAction(@AnyThread action: Action<Unit, *>, autoDisposeScope: ScopeProvider) {
        setOnClickListener { action() }
        action.isEnabled
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(autoDisposeScope).subscribe(::setEnabled)
        action.errors
            .autoDispose(autoDisposeScope).subscribe(::showError)
    }

    @AnyThread
    protected open fun showError(error: Throwable) {
        lifecycleScope.launchWhenStarted {
            AlertDialogFragment("Error", error.localizedMessage)
                .show(parentFragmentManager, null)
        }
    }

    override fun onStart() {
        super.onStart()
        val decorView = requireActivity().window.decorView
        if (isDarkStatusBar) {
            // TODO window inset controller compat in androidx.core >= 1.5.0
            decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)
        } else {
            decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }
}

private val inflateMethodMap = SimpleArrayMap<Class<out BaseFragment<out ViewBinding>>, Method>()

private fun <Binding : ViewBinding> Class<BaseFragment<Binding>>.inflateViewBinding(layoutInflater: LayoutInflater, inflateMethod: Method? = inflateMethodMap[this]): Binding {
    if (inflateMethod != null) {
        @Suppress("UNCHECKED_CAST")
        return inflateMethod(null, layoutInflater) as Binding
    }

    var clazz: Class<in BaseFragment<Binding>> = this
    while (true) {
        val genericSuperclass = clazz.genericSuperclass as? ParameterizedType
        genericSuperclass?.actualTypeArguments?.forEach { typeArgument ->
            if (typeArgument is Class<*> && ViewBinding::class.java.isAssignableFrom(typeArgument)) {
                val method = typeArgument.getMethod("inflate", LayoutInflater::class.java)
                inflateMethodMap.put(this, method)
                return inflateViewBinding(layoutInflater, method)
            }
        }
        clazz = clazz.superclass ?: error("Should never happen")
    }
}
