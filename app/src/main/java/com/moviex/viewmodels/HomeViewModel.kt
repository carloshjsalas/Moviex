package com.moviex.viewmodels

import androidx.lifecycle.*
import com.moviex.common.Outcome
import com.moviex.common.applySchedulers
import com.moviex.common.plusAssign
import com.moviex.controller.MoviesController
import com.moviex.model.MovieOrSeries
import com.moviex.model.actions.HomeActions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val moviesController: MoviesController
) : ViewModel(), LifecycleObserver {

    val outcome by lazy { MutableLiveData<Outcome<HomeActions>>() }

    private val disposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        getFullMovies()
        getFullSeries()
    }

    private fun getFullMovies() {
        disposable += Observable.combineLatest(
                moviesController.getMoviesPopular(1).toObservable(),
                moviesController.getMoviesTopRated(1).toObservable(),
                moviesController.getMoviesUpComing(1).toObservable(),
                Function3 { popular: List<MovieOrSeries>, topRated: List<MovieOrSeries>, upComing: List<MovieOrSeries> ->
                    return@Function3 Triple(popular, topRated, upComing)
                })
                .applySchedulers()
                .doOnSubscribe { outcome.value = Outcome.Progress(true) }
                .doFinally { outcome.value = Outcome.Progress(false) }
                .subscribe({
                    outcome.value = Outcome.success(HomeActions.OnMoviesFound(it.first, it.second, it.third))
                }, {
                    outcome.value = Outcome.failure(it)
                })
    }

    private fun getFullSeries() {
        disposable += Observable.combineLatest(
                moviesController.getSeriesPopular(1).toObservable(),
                moviesController.getSeriesTopRated(1).toObservable(),
                moviesController.getSeriesLatest(1).toObservable(),
                Function3 { popular: List<MovieOrSeries>, topRated: List<MovieOrSeries>, latest: List<MovieOrSeries> ->
                    return@Function3 Triple(popular, topRated, latest)
                })
                .applySchedulers()
                .subscribe({
                    outcome.value = Outcome.success(HomeActions.OnSeriesFound(it.first, it.second, it.third))
                }, {
                    outcome.value = Outcome.failure(it)
                })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        disposable.clear()
    }
}