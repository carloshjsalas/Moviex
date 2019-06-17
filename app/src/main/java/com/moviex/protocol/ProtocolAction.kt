package com.moviex.protocol

import com.moviex.model.CategoryType
import com.moviex.model.MovieOrSeries

sealed class ProtocolAction {
    object OnBackPressed : ProtocolAction()

    class OnGoToCategory(@CategoryType val categoryType: String) : ProtocolAction()
    class OnGoToDetailFromHome(val movieOrSeries: MovieOrSeries) : ProtocolAction()
    class OnGoToDetailFromCategory(val movieOrSeries: MovieOrSeries) : ProtocolAction()
}