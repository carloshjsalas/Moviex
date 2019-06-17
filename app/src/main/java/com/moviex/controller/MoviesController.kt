package com.moviex.controller

import com.moviex.R
import com.moviex.backend.MoviesRepository
import com.moviex.common.IResourceProvider
import com.moviex.model.CategoryType
import com.moviex.model.MOVIES_POPULAR
import com.moviex.model.MOVIES_TOP_RATED
import com.moviex.model.MOVIES_UPCOMING
import com.moviex.model.MovieOrSeries
import com.moviex.model.SERIES_LATEST
import com.moviex.model.SERIES_POPULAR
import com.moviex.model.SERIES_TOP_RATED
import io.reactivex.Single
import javax.inject.Inject

class MoviesController @Inject constructor(
        private val moviesRepository: MoviesRepository,
        private val resourceProvider: IResourceProvider
) {

    private var categoryPage = 0

    fun getMoviesPopular(page: Int): Single<List<MovieOrSeries>> {
        return moviesRepository.getMoviesPopular(page)
    }

    fun getMoviesTopRated(page: Int): Single<List<MovieOrSeries>> {
        return moviesRepository.getMoviesTopRated(page)
    }

    fun getMoviesUpComing(page: Int): Single<List<MovieOrSeries>> {
        return moviesRepository.getMoviesUpComing(page)
    }

    fun getSeriesLatest(page: Int): Single<List<MovieOrSeries>> {
        return moviesRepository.getSeriesLatest(page)
    }

    fun getSeriesPopular(page: Int): Single<List<MovieOrSeries>> {
        return moviesRepository.getSeriesPopular(page)
    }

    fun getSeriesTopRated(page: Int): Single<List<MovieOrSeries>> {
        return moviesRepository.getSeriesTopRated(page)
    }

    fun searchMoviesOrSeries(page: Int, searchQuery: String): Single<List<MovieOrSeries>> {
        return moviesRepository.searchMoviesOrSeries(page, searchQuery)
    }

    fun getMoviesOrSeriesByCategoryType(@CategoryType categoryType: String): Single<List<MovieOrSeries>> {
        categoryPage++
        return when (categoryType) {
            MOVIES_POPULAR -> getMoviesPopular(categoryPage)
            MOVIES_TOP_RATED -> getMoviesTopRated(categoryPage)
            MOVIES_UPCOMING -> getMoviesUpComing(categoryPage)
            SERIES_TOP_RATED -> getSeriesTopRated(categoryPage)
            SERIES_POPULAR -> getSeriesPopular(categoryPage)
            SERIES_LATEST -> getSeriesLatest(categoryPage)
            else -> Single.just(listOf())
        }
    }

    fun getCategoryTitle(@CategoryType categoryType: String): String {
        return when (categoryType) {
            MOVIES_POPULAR -> resourceProvider.getString(R.string.movies_title_popular)
            MOVIES_TOP_RATED -> resourceProvider.getString(R.string.movies_title_top_rated)
            MOVIES_UPCOMING -> resourceProvider.getString(R.string.movies_title_upcoming)
            SERIES_POPULAR -> resourceProvider.getString(R.string.series_title_popular)
            SERIES_LATEST -> resourceProvider.getString(R.string.series_title_latest)
            SERIES_TOP_RATED -> resourceProvider.getString(R.string.series_title_top_rated)
            else -> ""
        }
    }
}