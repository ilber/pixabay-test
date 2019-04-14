package com.ilber.pixabay

import com.ilber.pixabay.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasActivityInjector


class AppContext : DaggerApplication(), HasActivityInjector {

    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()


    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}