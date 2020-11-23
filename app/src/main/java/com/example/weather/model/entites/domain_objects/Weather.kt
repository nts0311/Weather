package com.example.weather.model.entites.domain_objects


data class WeatherEntity(
    var dbId: Int = 0,
    var baseId: Int = 0,
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)