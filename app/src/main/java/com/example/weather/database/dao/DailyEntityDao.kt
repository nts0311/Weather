package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.DailyEntity

@Dao
interface DailyEntityDao {
    @Insert
    suspend fun insertDailyEntity(dailyEntity: DailyEntity) : Long

    @Query("SELECT * FROM DailyEntity WHERE weatherInfoId=:weatherInfoId")
    suspend fun getDailyEntities(weatherInfoId:Int) : List<DailyEntity>
}