package com.moviex.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.moviex.common.Outcome
import com.moviex.controller.MoviesController
import com.moviex.di.qualifiers.MovieOrSeriesQualifier
import com.moviex.model.MovieOrSeries
import com.moviex.model.actions.DetailActions

class DetailViewModel constructor(
        @MovieOrSeriesQualifier private val movieOrSeries: MovieOrSeries?,
        private val moviesController: MoviesController
) : ViewModel(), LifecycleObserver {

    val outcome by lazy { MutableLiveData<Outcome<DetailActions>>() }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        movieOrSeries?.let { outcome.value = Outcome.success(DetailActions.OnShowMovieOrSeriesDetail(it)) }
    }
}