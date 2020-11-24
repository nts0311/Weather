package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.HourlyEntity

@Dao
interface HourlyEntityDao {
    @Insert
    suspend fun insertHourlyEntity(hourlyEntity: HourlyEntity) : Long

    @Query("SELECT * FROM HourlyEntity WHERE weatherInfoId=:weatherInfoId")
    suspend fun getHourlyEntities(weatherInfoId : Int) : List<HourlyEntity>
}