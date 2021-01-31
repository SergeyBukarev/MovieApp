package com.sergeybukarev.moviestestapp.presentation.screens.popularmovies

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import autodispose2.ScopeProvider
import autodispose2.androidx.lifecycle.autoDispose
import com.jakewharton.rxbinding4.view.visibility
import com.sergeybukarev.moviestestapp.R
import com.sergeybukarev.moviestestapp.core.platform.BaseFragment
import com.sergeybukarev.moviestestapp.databinding.FragmentMoviePopularsBinding
import com.sergeybukarev.moviestestapp.presentation.helpers.ShortScrollListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.deejdev.rxaction3.invoke
import toothpick.ktp.delegate.inject

class PopularMoviesFragment : BaseFragment<FragmentMoviePopularsBinding>() {
    interface Delegate {
        fun popularMoviesItemTap(movieId: Int)
    }

    private val delegate by inject<Delegate>()
    private val adapter by inject<PopularMoviesAdapter>()
    private val model by inject<PopularMoviesViewModel>()

    override fun onViewCreated(views: FragmentMoviePopularsBinding, savedInstanceState: Bundle?, whenViewDestroyed: ScopeProvider) {
        super.onViewCreated(views, savedInstanceState, whenViewDestroyed)
        adapter.onItemClick = { delegate.popularMoviesItemTap(it) }
        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        val spaceItemDecoration = SpacesItemDecoration(resources.getDimensionPixelOffset(R.dimen.videos_item_space))
        views.listView.layoutManager = layoutManager
        views.listView.addItemDecoration(spaceItemDecoration)
        views.listView.adapter = adapter
    }

    override fun onBindToModel(views: FragmentMoviePopularsBinding, whenViewDestroyed: ScopeProvider) {
        views.listView.addOnScrollListener(ShortScrollListener { lastVisiblePosition ->
            val fetchThreshold = 5
            if (lastVisiblePosition == RecyclerView.NO_POSITION || lastVisiblePosition + fetchThreshold >= adapter.itemCount) {
                model.fetchNextPageAction()
            }
        })
        model.items.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(adapter::updatePages)
        model.fetchNextPageAction.isExecuting.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(views.activityIndicatorView.visibility())
        model.fetchNextPageAction.errors.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(::showError)
    }

    override fun onViewBound(views: FragmentMoviePopularsBinding) {
        model.fetchNextPageAction()
    }
}
