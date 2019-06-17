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
import androidx.recyclerview.widget.GridLayoutManager
import com.moviex.R
import com.moviex.adapter.MoviesSeriesAdapter
import com.moviex.common.Outcome
import com.moviex.common.Utils
import com.moviex.common.onScrolledToEnd
import com.moviex.common.showOrHide
import com.moviex.controller.MoviesImagesController
import com.moviex.model.ListItem
import com.moviex.model.MovieOrSeries
import com.moviex.model.actions.CategoryActions
import com.moviex.protocol.ProtocolAction
import com.moviex.viewmodels.CategoryViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_category.*
import javax.inject.Inject

class CategoryFragment : BaseMoviesFragment() {

    companion object {
        const val CATEGORY_TYPE = "categoryType"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: CategoryViewModel

    @Inject
    lateinit var imagesController: MoviesImagesController

    private val numberOfCols by lazy { Utils.calculateNoOfColumns(requireContext()) }

    private val itemClickListener = object : MoviesSeriesAdapter.ItemClickListener {
        override fun onItemClicked(item: ListItem) {
            if (item is MovieOrSeries) {
                communication.onFragmentEvent(ProtocolAction.OnGoToDetailFromCategory(item))
            }
        }
    }

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
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { communication.onFragmentEvent(ProtocolAction.OnBackPressed) }
    }

    private fun bindViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel::class.java)
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

    private fun handleOnSuccess(action: CategoryActions) {
        when (action) {
            is CategoryActions.OnSetCategoryTitle -> updateCategoryTitle(action.categoryType)
            is CategoryActions.OnMoviesByCategoryFound -> addCategoryItems(action.items)
            is CategoryActions.OnMoviesByCategoryNextPage -> addNextPage(action.items)
        }
    }

    private fun updateCategoryTitle(categoryType: String) {
        toolbar.title = categoryType
    }

    private fun addCategoryItems(items: List<ListItem>) {
        categoryRecyclerView.run {
            adapter = MoviesSeriesAdapter(items.toMutableList(), imagesController, itemClickListener, numberOfCols)
            layoutManager = GridLayoutManager(context, numberOfCols)
            onScrolledToEnd { viewModel.loadNextCategoryPage() }
        }
    }

    private fun addNextPage(items: List<ListItem>) {
        (categoryRecyclerView.adapter as MoviesSeriesAdapter).addItems(items)
    }

    private fun handleLoading(isLoading: Boolean) {
        loadingProgress.showOrHide(isLoading)
    }

    private fun handleOnError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_LONG).show()
    }
}

