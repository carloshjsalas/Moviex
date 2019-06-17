package com.moviex.di

import com.moviex.backend.MoviesRepository
import com.moviex.backend.MoviesServices
import com.moviex.di.modules.CategoryModule
import com.moviex.di.modules.DetailModule
import com.moviex.di.modules.HomeModule
import com.moviex.di.qualifiers.ApiKeyQualifier
import com.moviex.di.scopes.FragmentScope
import com.moviex.fragments.CategoryFragment
import com.moviex.fragments.DetailFragment
import com.moviex.fragments.HomeFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class FragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindMainFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CategoryModule::class])
    abstract fun bindCategoryFragment(): CategoryFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [DetailModule::class])
    abstract fun bindDetailFragment(): DetailFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideMoviesService(retrofit: Retrofit): MoviesServices {
            return retrofit.create(MoviesServices::class.java)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideMoviesRepository(@ApiKeyQualifier apiKey: String, movieServices: MoviesServices): MoviesRepository {
            return MoviesRepository(apiKey, movieServices)
        }
    }
}
