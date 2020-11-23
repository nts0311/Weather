package com.example.weather.model.entites.data_transfer_objects

data class AirVisualResponse(val data: AvDataResponse)

data class AvDataResponse(val current: AvCurrentResponse)

data class AvCurrentResponse(val pollution: AvPollutionResponse)

data class AvPollutionResponse(val quius: Int)