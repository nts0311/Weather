package com.example.weather.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.weather.model.entites.domain_objects.WeatherInfo
import com.example.weather.repositories.WeatherInfoRepository
import kotlinx.coroutines.flow.Flow

class WeatherFragViewModel @ViewModelInject
constructor(private val weatherInfoRepository: WeatherInfoRepository) : ViewModel() {
    var locationId : Long = 0L
    val getWeatherInfo : Flow<WeatherInfo?> =
        weatherInfoRepository.getWeatherInfoFlow(locationId)

}