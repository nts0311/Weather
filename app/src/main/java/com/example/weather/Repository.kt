
package com.example.weather
/*
import android.util.Log
import com.example.weather.database.AppDatabase
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.database.room_entities.asDomainObject
import com.example.weather.database.room_entities.toDomainObject
import com.example.weather.model.entites.domain_objects.WeatherInfo
import com.example.weather.network.Resource
import com.example.weather.network.data_transfer_objects.OwmBaseResponse
import com.example.weather.network.data_transfer_objects.asDatabaseObject
import com.example.weather.network.performNetworkCall
import com.example.weather.network.services.AirVisualService
import com.example.weather.network.services.OpenWeatherMapService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val openWeatherMapService: OpenWeatherMapService,
    */
/*private val openUvService: OpenUvService,*//*

    private val airVisualService: AirVisualService,
    private val appDatabase: AppDatabase
) {
    var isNetWorkAvailable: Boolean = false

    fun getWeatherInfoFlow(location: LocationEntity): Flow<WeatherInfo?> = flow {
        val owmResponse = getWeatherInfo(location)
        when (owmResponse) {
            is Resource.Success -> {
                saveWeatherInfoToDb(owmResponse.data)
            }
            is Resource.Error -> {
                emit(getWeatherInfoFromDb(location))
            }
        }

        emit(getWeatherInfoFromDb(location))
    }
        .flowOn(Dispatchers.IO)

    suspend fun getWeatherInfo(location: LocationEntity) =
        performNetworkCall { openWeatherMapService.getWeatherInfo() }

    suspend fun getAirQualityIndex(location: LocationEntity) =
        performNetworkCall { airVisualService.getAirQualityIndex() }

    suspend fun addLocation(location: LocationEntity) {
        appDatabase.locationDao.insertLocation(location)
    }

    private suspend fun saveWeatherInfoToDb(owmBaseResponse: OwmBaseResponse) {
        appDatabase.apply {
            val weatherInfoId = weatherInfoDao.insertWeatherInfo(owmBaseResponse.asDatabaseObject())

            //Current Weather
            val currentEntity = owmBaseResponse.current.asDatabaseObject()
            currentEntity.weatherInfoId = weatherInfoId
            val currentEntityId = currentEntityDao.insertCurrentEntity(currentEntity)
            val currentWeatherList = owmBaseResponse.current.weather
                .asDatabaseObject()
                .onEach { it.currentWeatherId = currentEntityId }
            weatherEntityDao.insertWeatherEntities(currentWeatherList)


            val hourlyEntities = owmBaseResponse.hourly.asDatabaseObject()
                .onEach { it.weatherInfoId = weatherInfoId }
            val hourlyIds = hourlyEntityDao.insertHourlyEntities(hourlyEntities)
            var i = 0
            owmBaseResponse.hourly.forEach {
                val weatherList =
                    it.weather.asDatabaseObject().onEach { w -> w.hourlyWeatherId = hourlyIds[i] }
                i++
                weatherEntityDao.insertWeatherEntities(weatherList)
            }

            i = 0
            val dailyIds = dailyEntityDao.insertDailyEntities(
                owmBaseResponse.daily.asDatabaseObject()
                    .onEach { it.weatherInfoId = weatherInfoId })
            owmBaseResponse.daily.forEach {
                val weatherList =
                    it.weather.asDatabaseObject().onEach { w -> w.dailyWeatherId = dailyIds[i] }
                i++
                weatherEntityDao.insertWeatherEntities(weatherList)
            }
        }
    }


    private suspend fun getWeatherInfoFromDb(location: LocationEntity): WeatherInfo? {
        return appDatabase.run {
            val weatherInfoEntity =
                weatherInfoDao.getWeatherInfoByLocation(location.dbId) ?: return null

            val currentEntity = currentEntityDao.getCurrentEntity(weatherInfoEntity.dbId)
            val hourlyEntities = hourlyEntityDao.getHourlyEntities(weatherInfoEntity.dbId)
            val dailyEntities = dailyEntityDao.getDailyEntities(weatherInfoEntity.dbId)

            val currentWeather =
                weatherEntityDao.getCurrentWeatherEntities(currentEntity.dbId).toDomainObject()

            val hourlyWeather = hourlyEntities.map {
                val weatherRecords =
                    weatherEntityDao.getHourlyWeatherEntities(it.dbId).toDomainObject()
                it.asDomainObject(weatherRecords)
            }
            val dailyWeather = dailyEntities.map {
                val weatherRecords =
                    weatherEntityDao.getDailyWeatherEntities(it.dbId).toDomainObject()
                it.asDomainObject(weatherRecords)
            }
            weatherInfoEntity.asDomainObject(
                currentEntity.asDomainObject(currentWeather),
                hourlyWeather,
                dailyWeather
            )
        }
    }

    suspend fun deleteLocation(location: LocationEntity) {
        appDatabase.locationDao.deleteLocation(location)
    }
}*/
