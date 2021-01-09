package com.sergeybukarev.domain.gateways

import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMoviePage
import io.reactivex.rxjava3.core.Single

interface MovieGateway {
    fun getPopularMovies(page: Int): Single<ShortMoviePage>
    fun getMovieDetails(movieId: Int): Single<DetailedMovie>
    fun getCasts(movieId: Int): Single<List<Cast>>
}
