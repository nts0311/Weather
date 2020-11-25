package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.WeatherEntity

@Dao
interface WeatherEntityDao {
    @Insert
    suspend fun insertWeatherEntities(weatherEntities : List<WeatherEntity>)

    @Query("SELECT * FROM WeatherEntity WHERE currentWeatherId=:id")
    suspend fun getCurrentWeatherEntities(id: Long) : List<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity WHERE hourlyWeatherId=:id")
    suspend fun getHourlyWeatherEntities(id: Long) : List<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity WHERE dailyWeatherId=:id")
    suspend fun getDailyWeatherEntities(id: Long) : List<WeatherEntity>
}