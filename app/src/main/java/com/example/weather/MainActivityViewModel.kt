package com.example.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.repositories.LocationRepository
import com.example.weather.repositories.WeatherInfoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject
constructor(
    private val weatherRepository: WeatherInfoRepository,
    private val locationRepository: LocationRepository,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {
    var currentLocationId: Long = 1L

    private var updateDataJob : Job? = null

    private val _locationList = MutableLiveData<List<LocationEntity>>()
    val locationList : LiveData<List<LocationEntity>> = _locationList


    fun updateCurrentLocation(location: LocationEntity) {
        viewModelScope.launch {
            if (currentLocationId == -1L) {
                val id = locationRepository.insertLocation(location)
                location.dbId = id
                sharedPrefManager.setCurrentLocationId(id)
            } else
                locationRepository.updateLocation(location)

            updateData()
        }
    }

    fun updateData()
    {
        updateDataJob?.cancel()
         updateDataJob = viewModelScope.launch {
            val locations = locationRepository.getAllLocation()
             _locationList.value = locations
            weatherRepository.updateWeatherData(locations)
        }
    }
}