package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.WeatherEntity
import com.example.weather.model.entites.domain_objects.Weather


data class OwmWeather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

fun OwmWeather.asDatabaseObject(): WeatherEntity = WeatherEntity(id, main, description, icon)

fun OwmWeather.asDomainObject(): Weather =
    Weather(0, id = id, main = main, description = description, icon = icon)

fun List<OwmWeather>.asDatabaseObject() = map { it.asDatabaseObject() }

fun List<OwmWeather>.asDomainObject() = map { it.asDomainObject() }