package com.moviex.common

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.hasItems(): Boolean {
    return adapter?.let { it.itemCount > 0 } ?: false
}

fun RecyclerView.onScrolledToEnd(onLoadMoreListener: (() -> Unit)?) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0 && recyclerView.isLastItemDisplayed()) {
                onLoadMoreListener?.invoke()
            }
        }
    })
}

fun RecyclerView.isLastItemDisplayed(): Boolean {
    return hasItems().let { hasItems ->
        adapter?.let {
            val lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
            hasItems && ((lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == it.itemCount - 1))
        } ?: false
    }
}