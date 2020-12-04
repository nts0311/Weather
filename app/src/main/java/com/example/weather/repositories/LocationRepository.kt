package com.example.weather.repositories

import com.example.weather.database.AppDatabase
import com.example.weather.database.room_entities.LocationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val appDatabase: AppDatabase) {
    suspend fun updateLocation(locationEntity: LocationEntity)
    {
        appDatabase.locationDao.updateLocation(locationEntity)
    }

    suspend fun insertLocation(locationEntity: LocationEntity) : Long
        = appDatabase.locationDao.insertLocation(locationEntity)


    suspend fun getLocationById(id:Long) = appDatabase.locationDao.getLocation(id)

    suspend fun getAllLocation() = appDatabase.locationDao.getAllLocation()
}