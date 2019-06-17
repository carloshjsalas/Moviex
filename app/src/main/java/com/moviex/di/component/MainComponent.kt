package com.moviex.di.component

import android.app.Application
import com.moviex.MainApplication
import com.moviex.di.ActivityBuilder
import com.moviex.di.modules.MainAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        MainAppModule::class,
        ActivityBuilder::class
    ]
)
interface MainComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: MainApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MainComponent
    }
}
