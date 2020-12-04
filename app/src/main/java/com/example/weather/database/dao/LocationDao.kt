package com.example.weather.database.dao

import androidx.room.*
import com.example.weather.database.room_entities.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity): Long

    @Update
    suspend fun updateLocation(location: LocationEntity)

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Query("SELECT * FROM LocationEntity WHERE dbId = :locationId LIMIT 1")
    suspend fun getLocation(locationId: Long): LocationEntity

    @Query("SELECT * FROM LocationEntity")
    suspend fun getAllLocation() : List<LocationEntity>
}