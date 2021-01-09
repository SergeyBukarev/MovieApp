package com.sergeybukarev.moviestestapp.core.toothpick.modules

import com.sergeybukarev.apiclient.services.MovieApiService
import com.sergeybukarev.apiclient.toothpick.providers.MovieApiServiceProvider
import com.sergeybukarev.apiclient.toothpick.qualifiers.ApiBaseUrl
import com.sergeybukarev.apiclient.toothpick.qualifiers.ApiKey
import com.sergeybukarev.apiclient.toothpick.qualifiers.ApiScheduler
import com.sergeybukarev.moviestestapp.BuildConfig
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.HttpUrl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ApiModule : Module() {
    init {
        bind<HttpUrl>().withName(ApiBaseUrl::class).toInstance(BuildConfig.API_BASE_URL)
        bind<String>().withName(ApiKey::class).toInstance(BuildConfig.API_KEY)
        bind<Scheduler>().withName(ApiScheduler::class).toInstance(Schedulers.io())
        bind<MovieApiService>().toProvider(MovieApiServiceProvider::class).providesSingleton()
    }
}
