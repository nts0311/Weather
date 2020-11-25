package com.example.weather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.database.room_entities.UvIndexEntity

@Dao
interface UvIndexDao {
    @Insert
    suspend fun insertUvIndex(uvIndexEntity: UvIndexEntity) : Long

    @Query("SELECT * FROM UvIndexEntity WHERE weatherInfoId = :weatherInfoId LIMIT 1")
    suspend fun getUvIndex(weatherInfoId:Int) : UvIndexEntity
}