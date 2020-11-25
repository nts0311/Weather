package com.example.weather

import com.example.weather.database.AppDatabase
import com.example.weather.network.services.AirVisualService
import com.example.weather.network.services.OpenUvService
import com.example.weather.network.services.OpenWeatherMapService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val openWeatherMapService: OpenWeatherMapService,
    /*private val openUvService: OpenUvService,
    private val airVisualService: AirVisualService,*/
    private val appDatabase: AppDatabase
) {

}