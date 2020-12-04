package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.WeatherInfoEntity
import com.example.weather.model.entites.domain_objects.WeatherInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherInfoDao {
    @Insert
    suspend fun insertWeatherInfo(weatherInfo: WeatherInfoEntity) : Long

    @Query("SELECT * FROM WeatherInfoEntity WHERE locationId=:locationId LIMIT 1")
    suspend fun getWeatherInfoByLocation(locationId: Long): WeatherInfoEntity?

    @Query("SELECT * FROM WeatherInfoEntity WHERE locationId=:locationId LIMIT 1")
    fun getWeatherInfoByLocationFlow(locationId: Long): Flow<WeatherInfoEntity?>

    @Delete
    suspend fun deleteWeatherInfo(weatherInfo: WeatherInfoEntity)

    @Query("DELETE FROM WeatherInfoEntity WHERE locationId = :locationId")
    suspend fun deleteWeatherInfoWithLocationId(locationId: Long)
}