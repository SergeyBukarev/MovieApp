package com.sergeybukarev.moviestestapp.presentation.screens.popularmovies

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import autodispose2.ScopeProvider
import autodispose2.androidx.lifecycle.autoDispose
import com.jakewharton.rxbinding4.view.visibility
import com.sergeybukarev.moviestestapp.core.platform.BaseFragment
import com.sergeybukarev.moviestestapp.databinding.FragmentMoviePopularsBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.deejdev.rxaction3.invoke
import toothpick.ktp.delegate.inject

class PopularMoviesFragment : BaseFragment<FragmentMoviePopularsBinding>() {
    interface Delegate {
        fun popularMoviesItemTap(movieId: Int)
    }

    val delegate by inject<Delegate>()
    val adapter by inject<PopularMoviesAdapter>()
    val model by inject<PopularMoviesViewModel>()

    override fun onViewCreated(views: FragmentMoviePopularsBinding, savedInstanceState: Bundle?, whenViewDestroyed: ScopeProvider) {
        super.onViewCreated(views, savedInstanceState, whenViewDestroyed)
        adapter.onItemClick = { delegate.popularMoviesItemTap(it) }
        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        val spaceItemDecoration = SpacesItemDecoration(20)
        views.listView.layoutManager = layoutManager
        views.listView.addItemDecoration(spaceItemDecoration)
        views.listView.addOnScrollListener(ShortScrollListener(layoutManager) { model.prefetchIfNeeded(it) })
        views.listView.adapter = adapter
    }

    override fun onBindToModel(views: FragmentMoviePopularsBinding, whenViewDestroyed: ScopeProvider) {
        model.items.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe { adapter.addItems(it) }
        model.loadMoreAction.isExecuting.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(views.activityIndicatorView.visibility())
        model.loadMoreAction.errors.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(::showError)
    }

    override fun onViewBound(views: FragmentMoviePopularsBinding) {
        model.loadMoreAction()
    }
}

class ShortScrollListener(private val layoutManager: LinearLayoutManager, private val lastVisibleItemListener: (Int) -> Unit) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisible = layoutManager.findLastVisibleItemPosition()
        lastVisibleItemListener.invoke(lastVisible)
    }
}
