package com.example.weather.network

import retrofit2.Response
import java.lang.Exception

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}

suspend fun <T> performNetworkCall(call: suspend () -> Response<T>): Resource<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Resource.Success(body)
        }
        return Resource.Error("${response.code()} ${response.message()}")
    } catch (e: Exception) {
        return Resource.Error(e.message ?: e.toString())
    }
}

suspend fun <T> getData(
    hasGps: Boolean,
    hasNetwork: Boolean,
    hasGpsHasInternet: suspend () -> Resource<T>,
    hasGpsNoInternet: suspend () -> Resource<T>,
    noGpsHasInternet: suspend () -> Resource<T>,
    noGpsNoInternet: suspend () -> Resource<T>,
): Resource<T> {
    return if (hasNetwork) {
        if (hasGps) {
            hasGpsHasInternet()
        } else {
            noGpsHasInternet()
        }
    } else {
        if (hasGps) {
            hasGpsNoInternet()
        } else
            noGpsNoInternet()
    }
}