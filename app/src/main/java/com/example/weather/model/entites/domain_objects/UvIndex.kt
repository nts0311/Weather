package com.example.weather.model.entites.domain_objects

data class UvIndex(
    val dbId: Long,
    val weatherInfoId: Long,
    val uv: Double,
    val uvMax: Double
)