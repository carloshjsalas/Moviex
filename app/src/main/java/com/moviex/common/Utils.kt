package com.moviex.common

import android.content.Context
import android.net.ConnectivityManager
import com.moviex.R


class Utils {
    companion object {
        fun calculateNoOfColumns(context: Context): Int {
            val itemWithInDp: Int = (context.resources.getDimension(R.dimen.item_list_width) / context.resources.displayMetrics.density).toInt()
            val displayMetrics = context.resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            return (dpWidth / itemWithInDp).toInt()
        }

        fun calculateNewColumnWidth(context: Context, numberOfColumns: Int): Int {
            val displayMetrics = context.resources.displayMetrics
            return displayMetrics.widthPixels / numberOfColumns
        }

        fun amIConnected(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}