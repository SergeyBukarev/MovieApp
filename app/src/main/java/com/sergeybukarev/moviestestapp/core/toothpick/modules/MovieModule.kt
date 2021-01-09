package com.sergeybukarev.moviestestapp.core.toothpick.modules

import com.sergeybukarev.domain.gateways.MovieGateway
import com.sergeybukarev.moviestestapp.gateway.implementations.MovieGatewayImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MovieModule() : Module() {
    init {
        bind<MovieGateway>().toClass<MovieGatewayImpl>()
    }
}

