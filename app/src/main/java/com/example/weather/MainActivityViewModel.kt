package com.example.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.model.entites.domain_objects.WeatherInfo
import com.example.weather.network.Resource
import com.example.weather.repositories.LocationRepository
import com.example.weather.repositories.WeatherInfoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject
constructor(
    private val weatherRepository: WeatherInfoRepository,
    private val locationRepository: LocationRepository,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    var selectedLocationId: Long = 1L
    var currentLocationId: Long = 1L

    private val _weatherInfo = MutableLiveData<Resource<WeatherInfo?>>()
    val weatherInfo: LiveData<Resource<WeatherInfo?>> = _weatherInfo

    private val _selectedLocation = MutableLiveData<LocationEntity>()
    val selectedLocation: LiveData<LocationEntity> = _selectedLocation

    private var getDataJob: Job? = null

    fun getData(location: LocationEntity) {
        getDataJob?.cancel()

        getDataJob = viewModelScope.launch {
            val weatherInfo = weatherRepository.getWeatherData(location)
            _weatherInfo.value = weatherInfo
        }
    }

    fun setLocation(locationId: Long) {
        selectedLocationId = locationId
        viewModelScope.launch {
            val location = locationRepository.getLocationById(locationId)
            _selectedLocation.value = location
        }
    }

    fun updateAndSetCurrentLocation(location: LocationEntity) {
        viewModelScope.launch {
            if (currentLocationId == -1L) {
                val id = locationRepository.insertLocation(location)
                location.dbId = id
                sharedPrefManager.setCurrentLocationId(id)
            } else
                locationRepository.updateLocation(location)

            setLocation(location.dbId)
        }
    }
}