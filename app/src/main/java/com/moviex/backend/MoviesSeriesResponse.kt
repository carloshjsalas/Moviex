package com.moviex.backend

import com.google.gson.annotations.SerializedName
import com.moviex.model.MovieOrSeries


data class MoviesSeriesResponse(@SerializedName("results") val moviesOrSeries: MutableList<MovieOrSeries>)
