package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.HourlyEntity
import com.example.weather.model.entites.domain_objects.HourlyWeather


data class OwmHourly(
    val dt: Int,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val rain: OwmRain = OwmRain(),
    val snow: OwmSnow = OwmSnow(),
    val weather: List<OwmWeather>,
    val pop: Double
)

fun OwmHourly.asDatabaseObject(): HourlyEntity = HourlyEntity(
    dt,
    temp,
    feels_like,
    pressure,
    humidity,
    clouds,
    visibility,
    wind_speed,
    wind_deg,
    pop,
    rain.oneHour,
    snow.oneHour
)

fun OwmHourly.asDomainObject() = HourlyWeather(
    0,
    0,
    dt,
    temp,
    feels_like,
    pressure,
    humidity,
    clouds,
    visibility,
    wind_speed,
    wind_deg,
    pop,
    rain.oneHour,
    snow.oneHour,
    weather.asDomainObject()
)

fun List<OwmHourly>.asDatabaseObject() = map { it.asDatabaseObject() }

fun List<OwmHourly>.asDomainObject() = map { it.asDomainObject() }