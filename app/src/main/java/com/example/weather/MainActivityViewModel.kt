package com.example.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.model.entites.domain_objects.WeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject
constructor(private val repository: Repository) : ViewModel() {

    fun getWeatherInfoOfLocation(location: LocationEntity) : Flow<WeatherInfo?>
    {
        return repository.getWeatherInfoFlow(location)
    }

    fun insertLocation(location: LocationEntity)
    {
        viewModelScope.launch {
            repository.addLocation(location)
        }
    }

    fun setIsNetWorkAvailable(isAvailable : Boolean)
    {
        repository.isNetWorkAvailable = isAvailable
    }

    fun deleteLocation(location: LocationEntity) {
        viewModelScope.launch {
            repository.deleteLocation(location)
        }
    }

}