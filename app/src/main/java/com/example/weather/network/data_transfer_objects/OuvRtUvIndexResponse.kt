package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.UvIndexEntity
import com.example.weather.model.entites.domain_objects.UvIndex

data class OuvRtUvIndexResponse(
    val uv: Double,
    val uv_max: Double
)

fun OuvRtUvIndexResponse.asDatabaseObject(): UvIndexEntity = UvIndexEntity(uv, uv_max)

fun OuvRtUvIndexResponse.asDomainObject(): UvIndex = UvIndex(0, 0, uv, uv_max)