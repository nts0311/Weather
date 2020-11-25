package com.example.weather

import com.example.weather.database.AppDatabase
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.database.room_entities.asDomainObject
import com.example.weather.database.room_entities.toDomainObject
import com.example.weather.model.entites.domain_objects.WeatherInfo
import com.example.weather.network.data_transfer_objects.OwmBaseResponse
import com.example.weather.network.data_transfer_objects.asDatabaseObject
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
    /*private val openUvService: OpenUvService,
    private val airVisualService: AirVisualService,*/
    private val appDatabase: AppDatabase
) {
    fun getWeatherInfo(location: LocationEntity): Flow<WeatherInfo?> = flow {

        emit(getWeatherInfoFromDb(location))

        val owmResponse = openWeatherMapService.getWeatherInfo()

        if (owmResponse.isSuccessful) {
            saveWeatherInfoToDb(owmResponse.body()!!)
        }

    }
        .flowOn(Dispatchers.IO)

    private suspend fun saveWeatherInfoToDb(owmBaseResponse: OwmBaseResponse) {
        appDatabase.apply {
            weatherInfoDao.insertWeatherInfo(owmBaseResponse.asDatabaseObject())

            currentEntityDao.insertCurrentEntity(owmBaseResponse.current.asDatabaseObject())
            weatherEntityDao.insertWeatherEntities(owmBaseResponse.current.weather.map { it.asDatabaseObject() })

            hourlyEntityDao.insertHourlyEntities(owmBaseResponse.hourly.map { it.asDatabaseObject() })
            owmBaseResponse.hourly.forEach {
                weatherEntityDao.insertWeatherEntities(it.weather.map { w -> w.asDatabaseObject() })
            }

            dailyEntityDao.insertDailyEntities(owmBaseResponse.daily.map { it.asDatabaseObject() })
            owmBaseResponse.daily.forEach {
                weatherEntityDao.insertWeatherEntities(it.weather.map { w -> w.asDatabaseObject() })
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
                weatherEntityDao.getWeatherEntities(currentEntity.dbId).toDomainObject()

            val hourlyWeather = hourlyEntities.map {
                val weatherRecords = weatherEntityDao.getWeatherEntities(it.dbId).toDomainObject()
                it.asDomainObject(weatherRecords)
            }
            val dailyWeather = dailyEntities.map {
                val weatherRecords = weatherEntityDao.getWeatherEntities(it.dbId).toDomainObject()
                it.asDomainObject(weatherRecords)
            }
            weatherInfoEntity.asDomainObject(
                currentEntity.asDomainObject(currentWeather),
                hourlyWeather,
                dailyWeather
            )
        }
    }
}