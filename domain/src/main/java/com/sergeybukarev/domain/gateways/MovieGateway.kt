package com.sergeybukarev.domain.gateways

import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.domain.dto.DetailedMovie
import com.sergeybukarev.domain.dto.ShortMovie
import io.reactivex.rxjava3.core.Single

interface MovieGateway {
    fun getPopularMovie(page: Int): Single<List<ShortMovie>>
    fun getDetailMovie(movieId: Int): Single<DetailedMovie>
    fun getCasts(movieId: Int): Single<List<Cast>>
}
