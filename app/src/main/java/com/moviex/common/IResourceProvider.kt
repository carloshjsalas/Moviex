package com.moviex.common

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.FontRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface IResourceProvider {

    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg args: Any?): String

    fun getStringArray(@ArrayRes id: Int): Array<String>

    fun getQuantityString(@PluralsRes stringRes: Int, quantity: Int, vararg formatArgs: Any): String

    fun getFontTypeFace(@FontRes id: Int): Typeface?

    fun getColor(colorRes: Int): Int

    fun getDrawable(drawableRes: Int): Drawable
}