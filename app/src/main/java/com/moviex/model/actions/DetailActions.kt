package com.moviex.model.actions

import com.moviex.model.MovieOrSeries

sealed class DetailActions {
    class OnShowMovieOrSeriesDetail(val movieOrSeries: MovieOrSeries) : DetailActions()
}