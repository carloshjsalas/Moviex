package com.moviex.controller

import android.widget.ImageView
import com.moviex.common.downloadImage
import javax.inject.Inject

class MoviesImagesController @Inject constructor() {

    private val baseImageUrl = "https://image.tmdb.org/t/p/"
    private val imageTypeRegular = "w154"
    private val imageTypeOriginal = "original"

    fun retrieveImageForListItem(imageUrl: String?, imageView: ImageView) {
        if (imageUrl != null) {
            imageView.downloadImage(getUrlForRegularImage(imageUrl))
        }
    }

    fun retrieveOriginalImage(imageUrl: String?, imageView: ImageView) {
        if (imageUrl != null) {
            imageView.downloadImage(getUrlForOriginalImage(imageUrl))
        }
    }

    private fun getUrlForRegularImage(imageUrl: String) = baseImageUrl + imageTypeRegular + imageUrl

    private fun getUrlForOriginalImage(imageUrl: String) = baseImageUrl + imageTypeOriginal + imageUrl
}