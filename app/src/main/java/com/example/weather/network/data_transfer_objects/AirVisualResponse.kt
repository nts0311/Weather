package com.example.weather.network.data_transfer_objects

import com.example.weather.database.room_entities.AirQualityEntity

data class AirVisualResponse(val data: AvDataResponse)

data class AvDataResponse(val current: AvCurrentResponse)

data class AvCurrentResponse(val pollution: AvPollutionResponse)

data class AvPollutionResponse(val aqius: Int)

fun AirVisualResponse.asDatabaseObject(): AirQualityEntity =
    AirQualityEntity(data.current.pollution.aqius)