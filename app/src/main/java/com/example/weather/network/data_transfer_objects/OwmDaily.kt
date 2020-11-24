package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.DailyEntity


data class OwmDaily(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: OwmTemp,
    val feels_like: OwmFeelsLike,
    val pressure: Int,
    val humidity: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val weather: List<OwmWeather>,
    val clouds: Int,
    val pop: Double,
    val rain: Double = 0.0,
    val snow: Double = 0.0
)

fun OwmDaily.asDatabaseObject(): DailyEntity = DailyEntity(
    dt,
    sunrise,
    sunset,
    temp.day,
    temp.min,
    temp.max,
    temp.night,
    temp.eve,
    temp.morn,
    feels_like.day,
    feels_like.night,
    feels_like.eve,
    feels_like.morn,
    pressure,
    humidity,
    wind_speed,
    wind_deg,
    clouds,
    rain,
    snow,
    pop
)