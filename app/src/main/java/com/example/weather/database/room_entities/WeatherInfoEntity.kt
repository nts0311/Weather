package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weather.model.entites.domain_objects.CurrentWeather
import com.example.weather.model.entites.domain_objects.DailyWeather
import com.example.weather.model.entites.domain_objects.HourlyWeather
import com.example.weather.model.entites.domain_objects.WeatherInfo

@Entity(
    foreignKeys = [ForeignKey(
        entity = LocationEntity::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("locationId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class WeatherInfoEntity(
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0,
    var locationId: Long = 0
)

fun WeatherInfoEntity.asDomainObject(
    currentWeather: CurrentWeather,
    hourly: List<HourlyWeather>,
    daily: List<DailyWeather>
) = WeatherInfo(dbId, locationId, currentWeather, hourly, daily)
