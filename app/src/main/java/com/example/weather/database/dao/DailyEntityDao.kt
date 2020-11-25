package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.DailyEntity

@Dao
interface DailyEntityDao {
    @Insert
    suspend fun insertDailyEntities(dailyEntities: List<DailyEntity>): List<Long>

    @Query("SELECT * FROM DailyEntity WHERE weatherInfoId=:weatherInfoId ORDER BY dt DESC")
    suspend fun getDailyEntities(weatherInfoId: Long) : List<DailyEntity>
}