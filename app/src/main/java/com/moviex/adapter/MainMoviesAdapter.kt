package com.moviex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviex.R
import com.moviex.controller.MoviesImagesController
import com.moviex.model.ListFooter
import com.moviex.model.ListItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_title_list.*

class MainMoviesAdapter(
        private val sections: MutableList<Pair<String, List<ListItem>>>,
        private val imagesController: MoviesImagesController,
        private val itemClickListener: MoviesSeriesAdapter.ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainMoviesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_title_list, parent, false),
                imagesController,
                itemClickListener
        )
    }

    override fun getItemCount(): Int = sections.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = sections[position]
        (holder as MainMoviesViewHolder).bind(section.first, section.second)
    }

    fun addSection(moreSections: Pair<String, List<ListItem>>) {
        sections.add(moreSections)
        notifyDataSetChanged()
    }

    private class MainMoviesViewHolder(
            override val containerView: View,
            val imagesController: MoviesImagesController,
            val itemClickListener: MoviesSeriesAdapter.ItemClickListener
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(title: String, listItems: List<ListItem>) {
            titleTextView.text = title
            recyclerView.run {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = MoviesSeriesAdapter(listItems.toMutableList(), imagesController, itemClickListener, 0)
            }
        }
    }
}