package com.moviex.backend

import com.moviex.di.qualifiers.ApiKeyQualifier
import com.moviex.model.MOVIES_POPULAR
import com.moviex.model.MOVIES_TOP_RATED
import com.moviex.model.MOVIES_UPCOMING
import com.moviex.model.MovieOrSeries
import com.moviex.model.SERIES_LATEST
import com.moviex.model.SERIES_POPULAR
import com.moviex.model.SERIES_TOP_RATED
import io.reactivex.Single

class MoviesRepository constructor(
        @ApiKeyQualifier private val apiKey: String,
        private val moviesServices: MoviesServices
) {

    private val moviesOrSeriesCache = hashMapOf<Pair<String, Int>, List<MovieOrSeries>>()

    fun getMoviesPopular(page: Int): Single<List<MovieOrSeries>> {
        return if (moviesOrSeriesCache.contains(Pair(MOVIES_POPULAR, page))) {
            Single.just(moviesOrSeriesCache[Pair(MOVIES_POPULAR, page)])
        } else {
            moviesServices.getMoviesPopular(apiKey, page)
                    .map { it.moviesOrSeries.toList() }
                    .doOnSuccess { moviesOrSeriesCache[Pair(MOVIES_POPULAR, page)] = it }
        }
    }

    fun getMoviesUpComing(page: Int): Single<List<MovieOrSeries>> {
        return if (moviesOrSeriesCache.contains(Pair(MOVIES_UPCOMING, page))) {
            Single.just(moviesOrSeriesCache[Pair(MOVIES_UPCOMING, page)])
        } else {
            moviesServices.getMoviesUpComing(apiKey, page)
                    .map { it.moviesOrSeries.toList() }
                    .doOnSuccess { moviesOrSeriesCache[Pair(MOVIES_UPCOMING, page)] = it }
        }
    }

    fun getMoviesTopRated(page: Int): Single<List<MovieOrSeries>> {
        return if (moviesOrSeriesCache.contains(Pair(MOVIES_TOP_RATED, page))) {
            Single.just(moviesOrSeriesCache[Pair(MOVIES_TOP_RATED, page)])
        } else {
            moviesServices.getMoviesTopRated(apiKey, page)
                    .map { it.moviesOrSeries.toList() }
                    .doOnSuccess { moviesOrSeriesCache[Pair(MOVIES_TOP_RATED, page)] = it }
        }
    }

    fun getSeriesPopular(page: Int): Single<List<MovieOrSeries>> {
        return if (moviesOrSeriesCache.contains(Pair(SERIES_POPULAR, page))) {
            Single.just(moviesOrSeriesCache[Pair(SERIES_POPULAR, page)])
        } else {
            moviesServices.getSeriesPopular(apiKey, page)
                    .map { it.moviesOrSeries.toList() }
                    .doOnSuccess { moviesOrSeriesCache[Pair(SERIES_POPULAR, page)] = it }
        }
    }

    fun getSeriesLatest(page: Int): Single<List<MovieOrSeries>> {
        return if (moviesOrSeriesCache.contains(Pair(SERIES_LATEST, page))) {
            Single.just(moviesOrSeriesCache[Pair(SERIES_LATEST, page)])
        } else {
            moviesServices.getSeriesLatest(apiKey, page)
                    .map { it.moviesOrSeries.toList() }
                    .doOnSuccess { moviesOrSeriesCache[Pair(SERIES_LATEST, page)] = it }
        }
    }

    fun getSeriesTopRated(page: Int): Single<List<MovieOrSeries>> {
        return if (moviesOrSeriesCache.contains(Pair(SERIES_TOP_RATED, page))) {
            Single.just(moviesOrSeriesCache[Pair(SERIES_TOP_RATED, page)])
        } else {
            moviesServices.getSeriesTopRated(apiKey, page)
                    .map { it.moviesOrSeries.toList() }
                    .doOnSuccess { moviesOrSeriesCache[Pair(SERIES_TOP_RATED, page)] = it }
        }
    }

    fun searchMoviesOrSeries(page: Int, searchQuery: String): Single<List<MovieOrSeries>> {
        return moviesServices.getSearchMoviesOrSeries(apiKey, searchQuery, page)
                .map { it.moviesOrSeries }
    }
}