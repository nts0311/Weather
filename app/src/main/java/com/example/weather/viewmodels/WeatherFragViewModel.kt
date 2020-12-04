package com.example.weather.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.weather.repositories.WeatherInfoRepository

class WeatherFragViewModel @ViewModelInject
constructor(private val weatherInfoRepository: WeatherInfoRepository) : ViewModel() {
    var locationId: Long = 0L
    val getWeatherInfo = liveData {
        emitSource(weatherInfoRepository.getWeatherInfoFlow(locationId).asLiveData())
    }


}