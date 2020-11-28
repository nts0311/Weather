package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.AirQualityEntity
import com.example.weather.model.entites.domain_objects.AirQualityIndex

data class AirVisualResponse(
    val city: String,
    val location: AvLocation,
    val data: AvDataResponse
)

data class AvDataResponse(val current: AvCurrentResponse)

data class AvCurrentResponse(val pollution: AvPollutionResponse)

data class AvPollutionResponse(val aqius: Int)

data class AvLocation(
    val type: String,
    val coordinates: List<Double>
)

fun AirVisualResponse.asDatabaseObject(): AirQualityEntity =
    AirQualityEntity(data.current.pollution.aqius)

fun AirVisualResponse.asDomainObject(): AirQualityIndex =
    AirQualityIndex(0,0,data.current.pollution.aqius)