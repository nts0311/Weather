package com.example.weather.network.services

import com.example.weather.network.data_transfer_objects.AirVisualResponse
import retrofit2.Response
import retrofit2.http.GET

interface AirVisualService {
    @GET("b/E0M8/")
    suspend fun getAirQualityIndex() : Response<AirVisualResponse>
}