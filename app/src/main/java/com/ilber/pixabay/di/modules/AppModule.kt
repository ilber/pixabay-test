package com.ilber.pixabay.di.modules

import com.ilber.data.ImageRepositoryImpl
import com.ilber.domain.contracts.ImageRepository
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule {
    companion object {
        private const val BASE_URL = "https://pixabay.com/api/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @Singleton
    fun provideImageRepository(retrofit: Retrofit): ImageRepository =
        ImageRepositoryImpl(retrofit)
}