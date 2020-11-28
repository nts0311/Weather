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
import com.example.weather.network.getData
import com.example.weather.network.performNetworkCall
import com.example.weather.network.services.OpenWeatherMapService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherInfoRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val weatherService: OpenWeatherMapService,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun getWeatherData(
        hasGps: Boolean,
        hasNetwork: Boolean,
        currentLocation: LocationEntity
    ): Resource<WeatherInfo?> {
        return getData(
            hasGps, hasNetwork,
            hasGpsHasInternet = {
                return@getData when (val fetchingResult = fetchWeatherInfo(currentLocation)) {
                    is Resource.Error -> Resource.Error(fetchingResult.message)

                    is Resource.Success -> {
                        appDatabase.weatherInfoDao.deleteWeatherInfoWithLocationId(currentLocation.dbId)
                        saveWeatherInfoToDb(fetchingResult.data)
                        Resource.Success(fetchingResult.data.asDomainObject())
                    }
                }
            },
            hasGpsNoInternet = {
                Resource.Error("aaaa")
            },
            noGpsHasInternet = {
                Resource.Error("aaa")
            },
            noGpsNoInternet = {
                Resource.Error("aaa")
            }
        )
    }

    suspend fun <T, A> getData2(
        hasGps: Boolean,
        hasNetwork: Boolean,
        networkCall: suspend () -> Resource<T>,
        saveToDbQuery: suspend (T) -> Unit,
        getFromDbQuery: suspend () -> Resource<A>,
        transform : (T) -> A
    ): Resource<A> {

        if(hasNetwork)
        {
            if(hasGps)
            {
                when (val fetchingResult = networkCall())
                {
                    is Resource.Error -> Resource.Error<T>(fetchingResult.message)

                    is Resource.Success ->{
                        saveToDbQuery(fetchingResult.data)
                        return Resource.Success(transform(fetchingResult.data))
                    }
                }
            }
            else
            {

            }
        }
        else
        {

        }

        /*return if (hasNetwork) {
            return if (hasGps) {


                *//*when (val fetchingResult = networkCall()) {
                    is Resource.Error -> Resource.Error(fetchingResult.message)

                    is Resource.Success -> {
                        appDatabase.weatherInfoDao.deleteWeatherInfoWithLocationId(currentLocation.dbId)
                        saveToDbQuery(fetchingResult.data)
                        return Resource.Success(fetchingResult.data.asDomainObject())
                    }
                }*//*
            } else {

            }
        } else {
            if (hasGps) {

            } else

        }*/
    }


    suspend fun fetchWeatherInfo(location: LocationEntity) =
        performNetworkCall { weatherService.getWeatherInfo() }

    suspend fun saveWeatherInfoToDb(owmBaseResponse: OwmBaseResponse) {
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