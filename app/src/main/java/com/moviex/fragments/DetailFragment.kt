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
import com.moviex.R
import com.moviex.common.Outcome
import com.moviex.controller.MoviesImagesController
import com.moviex.model.MovieOrSeries
import com.moviex.model.actions.DetailActions
import com.moviex.protocol.ProtocolAction
import com.moviex.viewmodels.DetailViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.toolbar
import javax.inject.Inject

class DetailFragment : BaseMoviesFragment() {

    companion object {
        const val MOVIE_OR_SERIES = "movieOrSeries"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: DetailViewModel

    @Inject
    lateinit var imagesController: MoviesImagesController

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
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { communication.onFragmentEvent(ProtocolAction.OnBackPressed) }
    }

    private fun bindViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        lifecycle.addObserver(viewModel)
    }

    private fun listenToObserver() {
        viewModel.outcome.observe(this, Observer {
            when (it) {
                is Outcome.Success -> handleOnSuccess(it.data)
                is Outcome.Failure -> handleOnError(it.e)
            }
        })
    }

    private fun handleOnSuccess(action: DetailActions) {
        when (action) {
            is DetailActions.OnShowMovieOrSeriesDetail -> showDetail(action.movieOrSeries)
        }
    }

    private fun showDetail(movieOrSeries: MovieOrSeries) {
        collapsingToolbar.title = movieOrSeries.title ?: movieOrSeries.name
        imagesController.run {
            retrieveOriginalImage(movieOrSeries.backdropPath, coverImageView)
            retrieveImageForListItem(movieOrSeries.posterPath, posterImageView)
        }

        firstAirDateTextView.text = getString(R.string.first_air_date, movieOrSeries.releaseDate
                ?: (movieOrSeries.firstAirDate ?: ""))
        ratingTextView.text = getString(R.string.rating, movieOrSeries.voteAverage.toString())
        popularityTextView.text = getString(R.string.popularity, movieOrSeries.popularity.toString())
        overviewTextView.text = movieOrSeries.overview
    }

    private fun handleOnError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_LONG).show()
    }
}
