package com.example.weather.di

import com.example.weather.network.services.AirVisualService
import com.example.weather.network.services.OpenWeatherMapService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Provides
import javax.inject.Singleton
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi() =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    fun provideRetrofitBuilder(moshi: Moshi) =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())


    @Provides
    @Singleton
    fun provideOpenWeatherMapService(retrofitBuilder: Retrofit.Builder): OpenWeatherMapService =
        retrofitBuilder.baseUrl("https://jsonkeeper.com/") //testing
            .build()
            .create(OpenWeatherMapService::class.java)

    @Provides
    @Singleton
    fun provideAirVisualService(retrofitBuilder: Retrofit.Builder): AirVisualService =
        retrofitBuilder.baseUrl("https://jsonkeeper.com/") //testing
            .build()
            .create(AirVisualService::class.java)

}