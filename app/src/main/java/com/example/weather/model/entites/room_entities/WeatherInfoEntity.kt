package com.example.weather.model.entites.room_entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.model.entites.domain_objects.CurrentWeather
import com.example.weather.model.entites.domain_objects.DailyWeather
import com.example.weather.model.entites.domain_objects.HourlyWeather
import com.example.weather.model.entites.domain_objects.WeatherInfo

@Entity
data class WeatherInfoEntity(
    val dt: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Int = 0
}

fun WeatherInfoEntity.asDomainObject(
    currentWeather: CurrentWeather,
    hourly: List<HourlyWeather>,
    daily: List<DailyWeather>
) = WeatherInfo(dbId, dt, currentWeather, hourly, daily)
