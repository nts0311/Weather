package com.example.weather.di

import android.content.Context
import android.content.SharedPreferences
import com.example.weather.Constants
import com.example.weather.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context)
        = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPrefManager(sharedPreferences: SharedPreferences)
            = SharedPrefManager(sharedPreferences)
}