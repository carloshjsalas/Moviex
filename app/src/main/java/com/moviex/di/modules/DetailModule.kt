package com.moviex.di.modules

import androidx.lifecycle.ViewModel
import com.moviex.controller.MoviesController
import com.moviex.di.qualifiers.MovieOrSeriesQualifier
import com.moviex.di.scopes.ViewModelKey
import com.moviex.fragments.DetailFragment
import com.moviex.model.MovieOrSeries
import com.moviex.viewmodels.DetailViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class DetailModule {

    @Provides
    @MovieOrSeriesQualifier
    fun provideCategoryType(detailFragment: DetailFragment): MovieOrSeries? {
        return detailFragment.arguments?.getParcelable(DetailFragment.MOVIE_OR_SERIES)
    }

    @Provides
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindCategoryViewModel(@MovieOrSeriesQualifier movieOrSeries: MovieOrSeries?, moviesController: MoviesController): ViewModel {
        return DetailViewModel(movieOrSeries, moviesController)
    }

}