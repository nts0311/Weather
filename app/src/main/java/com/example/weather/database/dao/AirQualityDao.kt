package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.AirQualityEntity

@Dao
interface AirQualityDao {
    @Insert
    suspend fun insertAirQuality(entity: AirQualityEntity) : Long

    @Query("SELECT * FROM AirQualityEntity WHERE weatherInfoId = :weatherInfoId LIMIT 1")
    suspend fun getAirQuality(weatherInfoId: Int): AirQualityEntity
}