package com.example.weather.model.entites.data_transfer_objects

import com.squareup.moshi.Json

data class OwmRain(
    @Json(name = "1h")
    var oneHour : Double = 0.0
)