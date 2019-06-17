package com.moviex.model.actions

import com.moviex.model.ListItem

sealed class CategoryActions {
    class OnSetCategoryTitle(val categoryType: String) : CategoryActions()
    class OnMoviesByCategoryFound(val items: List<ListItem>) : CategoryActions()
    class OnMoviesByCategoryNextPage(val items: List<ListItem>) : CategoryActions()
}