package com.ilber.pixabay.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilber.pixabay.di.factories.ViewModelFactory
import com.ilber.pixabay.di.keys.ViewModelKey
import com.ilber.pixabay.views.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun searchViewModel(searchViewModel: SearchViewModel): ViewModel
}