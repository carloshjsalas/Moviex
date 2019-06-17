package com.moviex

import com.moviex.di.component.DaggerMainComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MainApplication : DaggerApplication() {

    companion object {
        lateinit var instance: MainApplication
            private set
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMainComponent.builder()
            .application(this)
            .build().apply { inject(this@MainApplication) }
    }
}