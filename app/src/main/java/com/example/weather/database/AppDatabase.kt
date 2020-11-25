package com.example.weather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.database.dao.*
import com.example.weather.database.room_entities.*

@Database(
    entities = [AirQualityEntity::class,
        CurrentEntity::class,
        DailyEntity::class,
        HourlyEntity::class,
        Location::class,
        UvIndexEntity::class,
        WeatherEntity::class,
        WeatherInfoEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val airQualityDao: AirQualityDao
    abstract val currentEntityDao: CurrentEntityDao
    abstract val dailyEntityDao: DailyEntityDao
    abstract val hourlyEntityDao: HourlyEntityDao
    abstract val locationDao: LocationDao
    abstract val uvIndexDao: UvIndexDao
    abstract val weatherEntityDao: WeatherEntityDao
    abstract val weatherInfoDao: WeatherInfoDao
}