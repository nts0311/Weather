package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.model.entites.domain_objects.WeatherInfo

@Dao
interface WeatherInfoDao {
    @Insert
    suspend fun insertWeatherInfo(weatherInfo: WeatherInfo)

    @Query("")
    suspend fun get
}