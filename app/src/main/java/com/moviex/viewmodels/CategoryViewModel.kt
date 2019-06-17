package com.moviex.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.moviex.common.Outcome
import com.moviex.common.applySchedulers
import com.moviex.common.plusAssign
import com.moviex.controller.MoviesController
import com.moviex.model.actions.CategoryActions
import io.reactivex.disposables.CompositeDisposable

class CategoryViewModel constructor(
        private val categoryType: String,
        private val moviesController: MoviesController
) : ViewModel(), LifecycleObserver {

    val outcome by lazy { MutableLiveData<Outcome<CategoryActions>>() }

    private val disposable = CompositeDisposable()

    private var isLoadingMoreCategory = false

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        updateCategoryTitle()
        getCategory()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        disposable.clear()
    }

    private fun updateCategoryTitle() {
        outcome.value = Outcome.success(CategoryActions.OnSetCategoryTitle(moviesController.getCategoryTitle(categoryType)))
    }

    fun loadNextCategoryPage() {
        if (!isLoadingMoreCategory) {
            disposable += moviesController.getMoviesOrSeriesByCategoryType(categoryType)
                    .applySchedulers()
                    .doOnSubscribe { isLoadingMoreCategory = true }
                    .doFinally { isLoadingMoreCategory = false }
                    .subscribe({
                        outcome.value = Outcome.success(CategoryActions.OnMoviesByCategoryNextPage(it))
                    }, {
                        outcome.value = Outcome.failure(it)
                    })
        }
    }

    private fun getCategory() {
        disposable += moviesController.getMoviesOrSeriesByCategoryType(categoryType)
                .applySchedulers()
                .doOnSubscribe { outcome.value = Outcome.Progress(true) }
                .doFinally { outcome.value = Outcome.Progress(false) }
                .subscribe({
                    outcome.value = Outcome.success(CategoryActions.OnMoviesByCategoryFound(it))
                }, {
                    outcome.value = Outcome.failure(it)
                })
    }
}