package com.moviex.di.modules

import androidx.lifecycle.ViewModel
import com.moviex.controller.MoviesController
import com.moviex.di.scopes.ViewModelKey
import com.moviex.fragments.CategoryFragment
import com.moviex.model.CategoryType
import com.moviex.viewmodels.CategoryViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class CategoryModule {

    @Provides
    @CategoryType
    fun provideCategoryType(categoryFragment: CategoryFragment): String {
        return categoryFragment.arguments?.getString(CategoryFragment.CATEGORY_TYPE).orEmpty()
    }

    @Provides
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    fun bindCategoryViewModel(@CategoryType categoryType: String, moviesController: MoviesController): ViewModel {
        return CategoryViewModel(categoryType, moviesController)
    }
}