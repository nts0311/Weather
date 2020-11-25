package com.example.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.model.entites.domain_objects.WeatherInfo
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel @ViewModelInject
constructor(private val repository: Repository) : ViewModel() {

    fun getWeatherInfoOfLocation(location: LocationEntity) : Flow<WeatherInfo?>
    {
        return repository.getWeatherInfo(location)
    }

}