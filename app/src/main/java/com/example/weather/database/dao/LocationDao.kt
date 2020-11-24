package com.example.weather.database.dao

import androidx.room.*
import com.example.weather.database.room_entities.Location

@Dao
interface LocationDao {
    @Insert
    suspend fun insertLocation(location: Location): Long

    @Update
    suspend fun updateLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)

    @Query("SELECT * FROM Location WHERE dbId = :locationId LIMIT 1")
    suspend fun getLocation(locationId: Long): Location
}