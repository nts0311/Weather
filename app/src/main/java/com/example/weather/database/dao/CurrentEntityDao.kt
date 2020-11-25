package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.CurrentEntity

@Dao
interface CurrentEntityDao {
    @Insert
    suspend fun insertCurrentEntity(currentEntity: CurrentEntity) : Long

    @Query("SELECT * FROM CurrentEntity WHERE weatherInfoId=:weatherInfoId LIMIT 1")
    suspend fun getCurrentEntity(weatherInfoId: Long): CurrentEntity
}