package com.example.weather.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Provides
import javax.inject.Singleton
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi() =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()




}