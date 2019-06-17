package com.moviex.model

import androidx.annotation.StringDef

interface ListItem

const val MOVIES_POPULAR = "movies_popular"
const val MOVIES_UPCOMING = "movies_upcoming"
const val MOVIES_TOP_RATED = "movies_top_rated"
const val SERIES_POPULAR = "series_popular"
const val SERIES_LATEST = "series_latest"
const val SERIES_TOP_RATED = "series_top_rated"

@StringDef(MOVIES_POPULAR, MOVIES_UPCOMING, MOVIES_TOP_RATED, SERIES_POPULAR, SERIES_LATEST, SERIES_TOP_RATED)
annotation class CategoryType