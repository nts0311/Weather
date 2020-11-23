package com.example.weather.model.entites.data_transfer_objects

import com.example.weather.model.entites.room_entities.WeatherInfoEntity


data class OwmBaseResponse(
    val dt: Int,
    val current: OwmCurrent,
    val hourly: List<OwmHourly>,
    val daily: List<OwmDaily>
)

fun OwmBaseResponse.asDatabaseObject(): WeatherInfoEntity = WeatherInfoEntity(dt)