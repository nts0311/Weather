package com.example.weather.network.services

import com.example.weather.network.data_transfer_objects.OuvRtUvIndexResponse
import retrofit2.Response
import retrofit2.http.GET

interface OpenUvService {
    @GET("")
    suspend fun getUvRealtimeIndex() : Response<OuvRtUvIndexResponse>
}