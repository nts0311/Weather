package com.example.weather.model.entites.data_transfer_objects

import com.example.weather.model.entites.room_entities.WeatherEntity


data class OwmWeather(
	val id: Int,
	val main: String,
	val description: String,
	val icon: String
)

fun OwmWeather.asDatabaseObject(): WeatherEntity = WeatherEntity(id, main, description, icon)