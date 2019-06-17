package com.moviex.backend

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesServices {

    @GET("movie/popular/")
    fun getMoviesPopular(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
    ): Single<MoviesSeriesResponse>

    @GET("movie/upcoming/")
    fun getMoviesUpComing(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
    ): Single<MoviesSeriesResponse>

    @GET("movie/top_rated/")
    fun getMoviesTopRated(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
    ): Single<MoviesSeriesResponse>

    @GET("tv/popular/")
    fun getSeriesPopular(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
    ): Single<MoviesSeriesResponse>

    @GET("tv/on_the_air/")
    fun getSeriesLatest(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
    ): Single<MoviesSeriesResponse>

    @GET("tv/top_rated/")
    fun getSeriesTopRated(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
    ): Single<MoviesSeriesResponse>

    @GET("search/multi/")
    fun getSearchMoviesOrSeries(
            @Query("api_key") apiKey: String,
            @Query("query") query: String,
            @Query("page") page: Int
    ): Single<MoviesSeriesResponse>
}