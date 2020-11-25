package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weather.model.entites.domain_objects.CurrentWeather
import com.example.weather.model.entites.domain_objects.Weather


@Entity(
    foreignKeys = [ForeignKey(
        entity = WeatherInfoEntity::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("weatherInfoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class CurrentEntity(
    val dt:Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val rain: Double,
    val snow: Double
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0
    var weatherInfoId: Long = 1
}

fun CurrentEntity.asDomainObject(weatherList: List<Weather>): CurrentWeather = CurrentWeather(
    dbId,
    weatherInfoId,
    sunrise,
    sunset,
    temp,
    feelsLike,
    pressure,
    humidity,
    clouds,
    visibility,
    windSpeed,
    windDeg,
    rain,
    snow,
    weatherList
)