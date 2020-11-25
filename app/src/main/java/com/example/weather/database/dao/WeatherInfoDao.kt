package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.WeatherInfoEntity
import com.example.weather.model.entites.domain_objects.WeatherInfo

@Dao
interface WeatherInfoDao {
    @Insert
    suspend fun insertWeatherInfo(weatherInfo: WeatherInfoEntity)

    @Query("SELECT * FROM WeatherInfoEntity WHERE locationId=:locationId ORDER BY dt DESC LIMIT 1")
    suspend fun getWeatherInfoByLocation(locationId: Long): WeatherInfoEntity?

    @Delete
    suspend fun deleteWeatherInfo(weatherInfo: WeatherInfoEntity)
}