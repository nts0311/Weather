package com.example.weather.model.entites.domain_objects

data class CurrentWeather(
    var dbId: Long = 0,
    var weatherInfoId: Long = 0,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val rain: Double,
    val snow: Double,
    val weather: List<Weather>
) {
}