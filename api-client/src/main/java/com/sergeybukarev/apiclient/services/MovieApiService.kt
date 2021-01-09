package com.sergeybukarev.apiclient.services

import com.sergeybukarev.apiclient.dto.responses.MovieCreditsResponse
import com.sergeybukarev.apiclient.dto.responses.MovieDetailsResponse
import com.sergeybukarev.apiclient.dto.responses.PopularMoviesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<PopularMoviesResponse>

    @GET("/3/movie/{movie}/credits")
    fun getMovieCredits(@Path("movie") movieId: Int): Single<MovieCreditsResponse>

    @GET("/3/movie/{movie}")
    fun getMovieDetails(@Path("movie") movieId: Int): Single<MovieDetailsResponse>
}
