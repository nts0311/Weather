package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weather.model.entites.domain_objects.DailyWeather
import com.example.weather.model.entites.domain_objects.Weather


@Entity(
    foreignKeys = [ForeignKey(
        entity = WeatherInfoEntity::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("weatherInfoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DailyEntity(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val dayTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val nightTemp: Double,
    val eveTemp: Double,
    val mornTemp: Double,
    val dayFeelsLike: Double,
    val nightFeelsLike: Double,
    val eveFeelsLike: Double,
    val mornFeelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val clouds: Int,
    val rain: Double,
    val snow: Double,
    val pop: Double,
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0
    var weatherInfoId: Long = 1
}

fun DailyEntity.asDomainObject(weather: List<Weather>): DailyWeather = DailyWeather(
    dbId,
    weatherInfoId,
    dt,
    sunrise,
    sunset,
    dayTemp,
    minTemp,
    maxTemp,
    nightTemp,
    eveTemp,
    mornTemp,
    dayFeelsLike,
    nightFeelsLike,
    eveFeelsLike,
    mornFeelsLike,
    pressure,
    humidity,
    windSpeed,
    windDeg,
    clouds,
    rain,
    snow,
    pop,
    weather
)