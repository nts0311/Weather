package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.WeatherInfoEntity
import com.example.weather.model.entites.domain_objects.WeatherInfo


data class OwmBaseResponse(
    val current: OwmCurrent,
    val hourly: List<OwmHourly>,
    val daily: List<OwmDaily>
)

fun OwmBaseResponse.asDatabaseObject(): WeatherInfoEntity = WeatherInfoEntity()

fun OwmBaseResponse.asDomainObject(): WeatherInfo =
    WeatherInfo(0, 0, current.asDomainObject(), hourly.asDomainObject(), daily.asDomainObject())