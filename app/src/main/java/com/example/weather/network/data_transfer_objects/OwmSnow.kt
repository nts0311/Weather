package com.example.weather.network.data_transfer_objects

import com.squareup.moshi.Json

data class OwmSnow(
    @Json(name = "1h")
    var oneHour: Double = 0.0
) {
}