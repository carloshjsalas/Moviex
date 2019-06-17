package com.moviex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.moviex.R
import com.moviex.common.Utils
import com.moviex.controller.MoviesImagesController
import com.moviex.model.ListFooter
import com.moviex.model.ListItem
import com.moviex.model.MovieOrSeries
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movies_series_footer_list.*
import kotlinx.android.synthetic.main.movies_series_item_list.*

class MoviesSeriesAdapter(
        private val moviesOrSeries: MutableList<ListItem>,
        private val imagesController: MoviesImagesController,
        private val itemClickListener: ItemClickListener,
        private val numberOfColumns: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = moviesOrSeries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_series_footer_list, parent, false)
                MovieSeriesFooterViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_series_item_list, parent, false)
                MovieSeriesItemViewHolder(view, imagesController, itemClickListener, numberOfColumns)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (moviesOrSeries[position]) {
            is ListFooter -> ITEM_TYPE_FOOTER
            else -> {
                ITEM_TYPE_MOVIE_OR_SERIES
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = moviesOrSeries[position]
        when (getItemViewType(position)) {
            ITEM_TYPE_FOOTER -> (holder as MovieSeriesFooterViewHolder).bind(item)
            ITEM_TYPE_MOVIE_OR_SERIES -> (holder as MovieSeriesItemViewHolder).bind(item)
        }
    }

    fun addItems(moviesOrSeriesToAdd: List<ListItem>) {
        val size = moviesOrSeries.size
        moviesOrSeries.addAll(moviesOrSeriesToAdd)
        notifyItemRangeInserted(size, moviesOrSeriesToAdd.size)
    }

    private class MovieSeriesItemViewHolder(
            override val containerView: View,
            private val imageRetriever: MoviesImagesController,
            private val itemClickListener: ItemClickListener,
            private val numberOfColumns: Int
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: ListItem) {
            val posterPath = (item as MovieOrSeries).posterPath
            imageRetriever.retrieveImageForListItem(posterPath, posterImageView)
            posterImageView.setOnClickListener {
                itemClickListener.onItemClicked(item)
            }
            if (numberOfColumns > 0) {
                calculateImageWidth(imageContainer, numberOfColumns)
            }
        }
    }

    private class MovieSeriesFooterViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: ListItem) {
            moreButton.setOnClickListener((item as ListFooter).onClickListener)
        }
    }

    interface ItemClickListener {
        fun onItemClicked(item: ListItem)
    }

    companion object {
        const val ITEM_TYPE_MOVIE_OR_SERIES = 1
        const val ITEM_TYPE_FOOTER = 2

        private fun calculateImageWidth(view: LinearLayout, numberOfColumns: Int) {
            val params = view.layoutParams as ConstraintLayout.LayoutParams
            params.width = Utils.calculateNewColumnWidth(view.context, numberOfColumns)
            view.requestLayout()
        }
    }
}