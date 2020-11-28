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


    /*fun getWeatherInfoOfLocation(location: LocationEntity) : Flow<WeatherInfo?>
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
    }*/

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
                val id = async { locationRepository.insertLocation(location) }
                sharedPrefManager.setCurrentLocationId(id.await())
            } else
                launch { locationRepository.updateLocation(location) }.join()
            setLocation(location.dbId)
        }
    }
}