package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.CurrentEntity
import com.example.weather.model.entites.domain_objects.CurrentWeather


data class OwmCurrent(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val rain: OwmRain = OwmRain(0.0),
    val snow: OwmSnow = OwmSnow(0.0),
    val weather: List<OwmWeather>
)

fun OwmCurrent.asDatabaseObject(): CurrentEntity = CurrentEntity(
    dt,
    sunrise,
    sunset,
    temp,
    feels_like,
    pressure,
    humidity,
    clouds,
    visibility,
    wind_speed,
    wind_deg,
    rain.oneHour,
    snow.oneHour
)

fun OwmCurrent.asDomainObject(): CurrentWeather = CurrentWeather(
    0,
    0,
    dt,
    sunrise,
    sunset,
    temp,
    feels_like,
    pressure,
    humidity,
    clouds,
    visibility,
    wind_speed,
    wind_deg,
    rain.oneHour,
    snow.oneHour,
    weather.asDomainObject()
)

