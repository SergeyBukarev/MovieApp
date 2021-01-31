package com.sergeybukarev.domain.gateways

import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMoviePage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MovieGateway {
    fun fetchPopular(page: Int): Single<ShortMoviePage>
    fun appendPopularInRam(item: ShortMoviePage): Completable
    fun observeRamPopular(): Observable<List<ShortMoviePage>>
    fun getLastPage(): Int?

    fun getMovieDetails(movieId: Int): Single<DetailedMovie>
    fun getCasts(movieId: Int): Single<List<Cast>>
}
