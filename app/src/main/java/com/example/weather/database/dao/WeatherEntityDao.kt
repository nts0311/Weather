package com.example.weather.database.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.WeatherEntity

interface WeatherEntityDao {
    @Insert
    suspend fun insertWeatherEntities(weatherEntities : List<WeatherEntity>) : List<Long>

    @Query("SELECT * FROM WeatherEntity WHERE baseId=:baseId")
    suspend fun getWeatherEntities(baseId : Int) : List<WeatherEntity>
}