package com.sergeybukarev.moviestestapp.core.toothpick.modules

import com.sergeybukarev.domain.toothpick.qualifiers.MovieId
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MovieDetailsModule(@MovieId movieId: Int) : Module() {
    init {
        bind<Int>().withName(MovieId::class).toInstance(movieId)
    }
}

