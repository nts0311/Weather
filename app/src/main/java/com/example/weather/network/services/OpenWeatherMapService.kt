package com.example.weather.network.services

import com.example.weather.network.data_transfer_objects.OwmBaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface OpenWeatherMapService {
    @GET("")
    suspend fun getWeatherInfo() : Response<OwmBaseResponse>
}