package com.sergeybukarev.moviestestapp.presentation.screens.moviedetails

import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import autodispose2.ScopeProvider
import autodispose2.androidx.lifecycle.autoDispose
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.visibility
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.moviestestapp.R
import com.sergeybukarev.moviestestapp.core.platform.BaseFragment
import com.sergeybukarev.moviestestapp.databinding.FragmentMovieDetailsBinding
import com.sergeybukarev.moviestestapp.gateway.mappers.DateMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.deejdev.rxaction3.invoke
import toothpick.ktp.delegate.inject

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    private val adapter by inject<CastAdapter>()
    private val model by inject<MovieDetailsViewModel>()
    private val dateMapper by inject<DateMapper>()

    override fun onViewCreated(views: FragmentMovieDetailsBinding, savedInstanceState: Bundle?, whenViewDestroyed: ScopeProvider) {
        super.onViewCreated(views, savedInstanceState, whenViewDestroyed)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        views.castListView.layoutManager = layoutManager
        views.castListView.adapter = adapter
    }

    override fun onBindToModel(views: FragmentMovieDetailsBinding, whenViewDestroyed: ScopeProvider) {
        model.castItems.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe { adapter.addItems(it) }
        model.loadCastAction.isExecuting.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(views.castsActivityIndicatorView.visibility())
        model.loadCastAction.errors.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(::showError)

        model.loadDetailsAction.isExecuting.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(views.activityIndicatorView.visibility())
        model.loadDetailsAction.errors.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(::showError)
        model.loadDetailsAction.values.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(::initViews)
    }

    private fun initViews(item: DetailedMovie) {
        requireViews().titleView.text = item.title

        val details = "${dateMapper.mapDate(item.releaseDate)}\n${dateMapper.mapDuration(item.runtime)}\n${item.genres.joinToString { it.name }}"
        requireViews().summaryView.text = getString(R.string.screen_movie_details_details_label, details)

        requireViews().markProgressView.progress = item.ratingPercentage
        requireViews().markView.text = item.ratingPercentage.toString()
        val hue = requireViews().markProgressView.angle / 3
        requireViews().markProgressView.color = Color.HSVToColor(floatArrayOf(hue, 1F, 1F))
        requireViews().overviewView.text = item.overview

        requireView().post {
            Glide.with(requireViews().cardImageView).load(item.posterImage).centerCrop().placeholder(R.drawable.image_placeholder).into(requireViews().cardImageView)
            Glide.with(requireViews().backImageView).load(item.backdropImage).centerCrop().placeholder(R.drawable.image_placeholder).into(requireViews().backImageView)
        }
    }

    override fun onViewBound(views: FragmentMovieDetailsBinding) {
        model.loadCastAction()
        model.loadDetailsAction()
    }
}
