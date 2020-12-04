package com.example.weather.worker

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weather.LocationTracker
import com.example.weather.SharedPrefManager
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.repositories.LocationRepository
import com.example.weather.repositories.WeatherInfoRepository
import kotlinx.coroutines.*
import kotlin.coroutines.resume

class UpdateDataWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private var weatherInfoRepository: WeatherInfoRepository,
    private var locationRepository: LocationRepository,
    private var sharedPreferences: SharedPreferences,
) : CoroutineWorker(appContext, workerParams) {
    lateinit var sharedPrefManager :SharedPrefManager
    private val appContext = appContext

    override suspend fun doWork(): Result {
        val result = updateCurrentLocation()
        val locations = locationRepository.getAllLocation()
        weatherInfoRepository.updateWeatherData(locations)

        return result
    }

    private suspend fun updateCurrentLocation() : Result
    {
        sharedPrefManager = SharedPrefManager(sharedPreferences)
        val currentLocationId = sharedPrefManager.getCurrentLocationId()

        if(currentLocationId == -1L)
            return Result.failure()

        val hasGps = LocationTracker.isLocationEnabled(appContext)

        val currentLocation: LocationEntity = locationRepository.getLocationById(currentLocationId)

        if (hasGps) {
            try {
                val curLocation = getCurrentLocationTimeout()
                currentLocation.lat = curLocation.lat
                currentLocation.long = curLocation.long
            } catch (e: TimeoutCancellationException) {
                return Result.retry()
            }

            locationRepository.updateLocation(currentLocation)

        }
        return Result.success()
    }

    private suspend fun getCurrentLocationTimeout() = withTimeout(30000) {
        suspendCancellableCoroutine<LocationEntity> { cont ->
            LocationTracker.getCurrentLocation(appContext)
            {
                cont.resume(LocationEntity(it.latitude, it.longitude))
            }
        }
    }

    companion object{
        const val UNIQUE_WORK_NAME = "update_weather_data_work"
    }
}