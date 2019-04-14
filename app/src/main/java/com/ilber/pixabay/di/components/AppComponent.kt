package com.ilber.pixabay.di.components

import android.app.Application
import com.ilber.pixabay.AppContext
import com.ilber.pixabay.di.modules.ActivityModule
import com.ilber.pixabay.di.modules.ApplicationModule
import com.ilber.pixabay.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityModule::class,
        ApplicationModule::class,
        AndroidInjectionModule::class,
        ViewModelModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: AppContext)
}