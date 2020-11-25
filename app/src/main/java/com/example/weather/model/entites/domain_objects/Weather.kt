package com.example.weather.model.entites.domain_objects


data class Weather(
    var dbId: Long = 0,
    var currentWeatherId: Long? = null,
    var hourlyWeatherId: Long? = null,
    var dailyWeatherId: Long? = null,
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)