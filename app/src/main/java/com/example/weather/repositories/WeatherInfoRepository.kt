package com.example.weather.repositories

import android.content.SharedPreferences
import com.example.weather.database.AppDatabase
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.database.room_entities.WeatherInfoEntity
import com.example.weather.database.room_entities.asDomainObject
import com.example.weather.database.room_entities.toDomainObject
import com.example.weather.model.entites.domain_objects.WeatherInfo
import com.example.weather.network.Resource
import com.example.weather.network.data_transfer_objects.OwmBaseResponse
import com.example.weather.network.data_transfer_objects.asDatabaseObject
import com.example.weather.network.data_transfer_objects.asDomainObject
import com.example.weather.network.services.OpenWeatherMapService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.yield
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherInfoRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val weatherService: OpenWeatherMapService
) : BaseRepository() {

    suspend fun updateWeatherData(locations: List<LocationEntity>): Boolean {
        var success: Boolean
        for (l in locations) {
            yield()
            success = updateData(
                networkCall = {
                    fetchWeatherInfo(l)
                },
                saveToDbQuery = {
                    deleteOldData(l.dbId)
                    saveWeatherInfoToDb(it, l.dbId)
                }
            )

            if (!success) return false
        }
        return true
    }

    private suspend fun fetchWeatherInfo(location: LocationEntity) =
        performNetworkCall { weatherService.getWeatherInfo() }

    private suspend fun deleteOldData(locationId: Long) {
        appDatabase.weatherInfoDao.deleteWeatherInfoWithLocationId(locationId)
    }

    private suspend fun saveWeatherInfoToDb(owmBaseResponse: OwmBaseResponse, locationId: Long) {
        appDatabase.apply {
            val weatherInfo = owmBaseResponse.asDatabaseObject()
            weatherInfo.locationId = locationId
            val weatherInfoId = weatherInfoDao.insertWeatherInfo(weatherInfo)

            //Current Weather
            val currentEntity = owmBaseResponse.current.asDatabaseObject()
            currentEntity.weatherInfoId = weatherInfoId
            val currentEntityId = currentEntityDao.insertCurrentEntity(currentEntity)
            val currentWeatherList = owmBaseResponse.current.weather
                .asDatabaseObject()
                .onEach { it.currentWeatherId = currentEntityId }
            weatherEntityDao.insertWeatherEntities(currentWeatherList)


            val hourlyEntities = owmBaseResponse.hourly.take(6)
            val hourlyIds = hourlyEntityDao.insertHourlyEntities(
                hourlyEntities.asDatabaseObject().onEach { it.weatherInfoId = weatherInfoId })
            var i = 0
            hourlyEntities.forEach {
                val weatherList =
                    it.weather.asDatabaseObject().onEach { w -> w.hourlyWeatherId = hourlyIds[i] }
                i++
                weatherEntityDao.insertWeatherEntities(weatherList)
            }

            i = 0
            val dailyEntities = owmBaseResponse.daily.take(6)
            val dailyIds = dailyEntityDao.insertDailyEntities(
                    dailyEntities.asDatabaseObject().onEach { it.weatherInfoId = weatherInfoId })
            dailyEntities.forEach {
                val weatherList =
                    it.weather.asDatabaseObject().onEach { w -> w.dailyWeatherId = dailyIds[i] }
                i++
                weatherEntityDao.insertWeatherEntities(weatherList)
            }
        }
    }


    private suspend fun getWeatherInfoFromDb(weatherInfoEntity: WeatherInfoEntity): WeatherInfo {
        return appDatabase.run {
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

    fun getWeatherInfoFlow(locationId: Long): Flow<WeatherInfo?> =
        appDatabase.weatherInfoDao.getWeatherInfoByLocationFlow(locationId)
            .map {
                if (it == null) null
                else getWeatherInfoFromDb(it)
            }
            .distinctUntilChanged()
}