package com.example.weather.di

import com.squareup.moshi.Moshi
import dagger.Provides
import javax.inject.Singleton


object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi() =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
}