package com.moviex

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.moviex.common.Utils
import com.moviex.fragments.CategoryFragmentDirections
import com.moviex.fragments.HomeFragmentDirections
import com.moviex.model.CategoryType
import com.moviex.model.MovieOrSeries
import com.moviex.protocol.CommunicationCallback
import com.moviex.protocol.ProtocolAction
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(), CommunicationCallback {

    private lateinit var hostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
        checkInternet()
    }

    override fun onFragmentEvent(action: ProtocolAction) {
        when (action) {
            is ProtocolAction.OnGoToCategory -> launchCategoryFragment(action.categoryType)
            is ProtocolAction.OnGoToDetailFromHome -> launchDetailFragmentFromHome(action.movieOrSeries)
            is ProtocolAction.OnGoToDetailFromCategory -> launchDetailFragmentFromCategory(action.movieOrSeries)
            is ProtocolAction.OnBackPressed -> onBackPressed()
        }
    }

    private fun checkInternet() {
        if (!Utils.amIConnected(this)) {
            Toast.makeText(this, getString(R.string.internet_error), Toast.LENGTH_LONG).show()
        }
    }
    private fun setupNavigation() {
        hostFragment = supportFragmentManager.findFragmentById(R.id.mainNavigationFragment) as NavHostFragment
    }

    private fun launchCategoryFragment(@CategoryType categoryType: String) {
        hostFragment.navController.navigate(HomeFragmentDirections.homeToCategoryDestination(categoryType))
    }

    private fun launchDetailFragmentFromHome(movieOrSeries: MovieOrSeries) {
        hostFragment.navController.navigate(HomeFragmentDirections.homeToDetailDestination(movieOrSeries))
    }

    private fun launchDetailFragmentFromCategory(movieOrSeries: MovieOrSeries) {
        hostFragment.navController.navigate(CategoryFragmentDirections.categoryToDetailDestination(movieOrSeries))
    }
}
