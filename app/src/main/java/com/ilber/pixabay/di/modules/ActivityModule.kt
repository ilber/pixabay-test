package com.ilber.pixabay.di.modules

import com.ilber.pixabay.views.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeSearchActivity(): SearchActivity
}