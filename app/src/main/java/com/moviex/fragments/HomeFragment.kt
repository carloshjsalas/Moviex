package com.moviex.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviex.R
import com.moviex.adapter.MainMoviesAdapter
import com.moviex.adapter.MoviesSeriesAdapter
import com.moviex.common.Outcome
import com.moviex.common.showOrHide
import com.moviex.controller.MoviesImagesController
import com.moviex.model.CategoryType
import com.moviex.model.ListFooter
import com.moviex.model.ListItem
import com.moviex.model.MOVIES_POPULAR
import com.moviex.model.MOVIES_TOP_RATED
import com.moviex.model.MOVIES_UPCOMING
import com.moviex.model.MovieOrSeries
import com.moviex.model.SERIES_LATEST
import com.moviex.model.SERIES_POPULAR
import com.moviex.model.SERIES_TOP_RATED
import com.moviex.model.actions.HomeActions
import com.moviex.protocol.ProtocolAction
import com.moviex.viewmodels.HomeViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseMoviesFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var imagesController: MoviesImagesController

    private val itemClickListener = object : MoviesSeriesAdapter.ItemClickListener {
        override fun onItemClicked(item: ListItem) {
            if (item is MovieOrSeries) {
                communication.onFragmentEvent(ProtocolAction.OnGoToDetailFromHome(item))
            }
        }
    }

    private lateinit var mainListAdapter: MainMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        listenToObserver()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainListAdapter = MainMoviesAdapter(mutableListOf(), imagesController, itemClickListener)
        mainHomeRecyclerView.run {
            adapter = mainListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun bindViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        lifecycle.addObserver(viewModel)
    }

    private fun listenToObserver() {
        viewModel.outcome.observe(this, Observer {
            when (it) {
                is Outcome.Success -> handleOnSuccess(it.data)
                is Outcome.Progress -> handleLoading(it.loading)
                is Outcome.Failure -> handleOnError(it.e)
            }
        })
    }

    private fun handleOnSuccess(action: HomeActions) {
        when (action) {
            is HomeActions.OnMoviesFound -> showMovies(action.popular, action.topRated, action.upComing)
            is HomeActions.OnSeriesFound -> showSeries(action.popular, action.topRated, action.latest)
        }
    }

    private fun showMovies(popular: List<ListItem>, topRated: List<ListItem>, upComing: List<ListItem>) {
        if (popular.isNotEmpty()) {
            addSection(Pair(getString(R.string.movies_title_popular), popular.toMutableList().apply {
                add(ListFooter(onMoreClickListener(MOVIES_POPULAR)))
            }))
        }

        if (topRated.isNotEmpty()) {
            addSection(Pair(getString(R.string.movies_title_top_rated), topRated.toMutableList().apply {
                add(ListFooter(onMoreClickListener(MOVIES_TOP_RATED)))
            }))
        }

        if (upComing.isNotEmpty()) {
            addSection(Pair(getString(R.string.movies_title_upcoming), upComing.toMutableList().apply {
                add(ListFooter(onMoreClickListener(MOVIES_UPCOMING)))
            }))
        }
    }

    private fun showSeries(popular: List<ListItem>, topRated: List<ListItem>, latest: List<ListItem>) {
        if (popular.isNotEmpty()) {
            addSection(Pair(getString(R.string.series_title_popular), popular.toMutableList().apply {
                add(ListFooter(onMoreClickListener(SERIES_POPULAR)))
            }))
        }

        if (topRated.isNotEmpty()) {
            addSection(Pair(getString(R.string.series_title_top_rated), topRated.toMutableList().apply {
                add(ListFooter(onMoreClickListener(SERIES_TOP_RATED)))
            }))
        }

        if (latest.isNotEmpty()) {
            addSection(Pair(getString(R.string.series_title_latest), latest.toMutableList().apply {
                add(ListFooter(onMoreClickListener(SERIES_LATEST)))
            }))
        }
    }

    private fun addSection(section: Pair<String, List<ListItem>>) {
        mainListAdapter.addSection(section)
    }

    private fun handleLoading(isLoading: Boolean) {
        loadingProgress.showOrHide(isLoading)
    }

    private fun handleOnError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_LONG).show()
    }

    private fun onMoreClickListener(@CategoryType categoryType: String) = View.OnClickListener {
        communication.onFragmentEvent(ProtocolAction.OnGoToCategory(categoryType))
    }
}
