package com.moviex.model.actions

import com.moviex.model.ListItem

sealed class HomeActions {

    class OnMoviesFound(
        val popular: List<ListItem>,
        val topRated: List<ListItem>,
        val upComing: List<ListItem>
    ) : HomeActions()

    class OnSeriesFound(
            val popular: List<ListItem>,
            val topRated: List<ListItem>,
            val latest: List<ListItem>
    ) : HomeActions()
}