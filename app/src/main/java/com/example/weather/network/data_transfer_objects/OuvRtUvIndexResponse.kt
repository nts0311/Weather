package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.UvIndexEntity

data class OuvRtUvIndexResponse(
    val uv: Double,
    val uv_max: Double
)

fun OuvRtUvIndexResponse.asDatabaseObject(): UvIndexEntity = UvIndexEntity(uv, uv_max)