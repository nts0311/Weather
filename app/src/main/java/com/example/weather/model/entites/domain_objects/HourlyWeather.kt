package com.example.weather.model.entites.domain_objects

class HourlyWeather(
    var dbId: Int,
    var weatherInfoId: Int,
    val dt: Int,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val pop: Int,
    val rain: Double,
    val snow: Double
) {
}