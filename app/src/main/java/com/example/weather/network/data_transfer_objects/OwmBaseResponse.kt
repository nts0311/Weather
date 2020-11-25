package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.WeatherInfoEntity


data class OwmBaseResponse(
    val current: OwmCurrent,
    val hourly: List<OwmHourly>,
    val daily: List<OwmDaily>
)

fun OwmBaseResponse.asDatabaseObject(): WeatherInfoEntity = WeatherInfoEntity()