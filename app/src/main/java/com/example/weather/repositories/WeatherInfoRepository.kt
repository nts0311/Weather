package com.example.weather.repositories

import android.content.SharedPreferences
import com.example.weather.database.AppDatabase
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.database.room_entities.asDomainObject
import com.example.weather.database.room_entities.toDomainObject
import com.example.weather.model.entites.domain_objects.WeatherInfo
import com.example.weather.network.Resource
import com.example.weather.network.data_transfer_objects.OwmBaseResponse
import com.example.weather.network.data_transfer_objects.asDatabaseObject
import com.example.weather.network.data_transfer_objects.asDomainObject
import com.example.weather.network.services.OpenWeatherMapService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherInfoRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val weatherService: OpenWeatherMapService,
    private val sharedPreferences: SharedPreferences
) : BaseRepository() {

    suspend fun getWeatherData(
        location: LocationEntity
    ): Resource<WeatherInfo?> {

        return getData(
            networkCall = {
                fetchWeatherInfo(location)
            },
            saveToDbQuery = { response ->
                deleteOldData(location.dbId)
                saveWeatherInfoToDb(response, location.dbId)
            },
            getFromDbQuery = {
                getWeatherInfoFromDb(location)
            },
            transform = {
                it.asDomainObject()
            })
    }




    suspend fun fetchWeatherInfo(location: LocationEntity) =
        performNetworkCall { weatherService.getWeatherInfo() }

    suspend fun deleteOldData(locationId : Long)
    {
        appDatabase.weatherInfoDao.deleteWeatherInfoWithLocationId(locationId)
    }

    suspend fun saveWeatherInfoToDb(owmBaseResponse: OwmBaseResponse, locationId : Long) {
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


    suspend fun getWeatherInfoFromDb(location: LocationEntity): WeatherInfo? {
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

}