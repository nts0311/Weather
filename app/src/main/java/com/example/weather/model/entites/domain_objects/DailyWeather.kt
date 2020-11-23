package com.example.weather.model.entites.domain_objects

data class DailyWeather(
    var dbId: Int,
    var weatherInfoId: Int,
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val dayTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val nightTemp: Double,
    val eveTemp: Double,
    val mornTemp: Double,
    val dayFeelsLike: Double,
    val nightFeelsLike: Double,
    val eveFeelsLike: Double,
    val mornFeelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val clouds: Int,
    val rain: Double,
    val snow: Double,
    val pop: Double,
    val weather: List<Weather>,
) {
}