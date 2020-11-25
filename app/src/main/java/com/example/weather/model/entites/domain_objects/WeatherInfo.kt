package com.example.weather.model.entites.domain_objects

data class WeatherInfo(
    var dbId: Long,
    var locationId: Long,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)