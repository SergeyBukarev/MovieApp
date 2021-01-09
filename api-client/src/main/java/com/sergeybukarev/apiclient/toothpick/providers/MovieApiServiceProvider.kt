package com.sergeybukarev.apiclient.toothpick.providers

import com.sergeybukarev.apiclient.BuildConfig
import com.sergeybukarev.apiclient.auth.AddApiKeyInterceptor
import com.sergeybukarev.apiclient.services.MovieApiService
import com.sergeybukarev.apiclient.toothpick.qualifiers.ApiBaseUrl
import com.sergeybukarev.apiclient.toothpick.qualifiers.ApiScheduler
import io.reactivex.rxjava3.core.Scheduler
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class MovieApiServiceProvider(
    @ApiBaseUrl private val baseUrl: HttpUrl,
    @ApiScheduler private val scheduler: Scheduler,
    private val addApiKeyInterceptor: AddApiKeyInterceptor,
) : Provider<MovieApiService> {
    override fun get(): MovieApiService {
        val client = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            }
            .addInterceptor(addApiKeyInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(scheduler))
            .build()

        return retrofit.create(MovieApiService::class.java)
    }
}
