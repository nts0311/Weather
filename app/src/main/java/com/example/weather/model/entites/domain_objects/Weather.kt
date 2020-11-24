package com.example.weather.model.entites.domain_objects


data class Weather(
    var dbId: Long = 0,
    var baseId: Long = 0,
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)