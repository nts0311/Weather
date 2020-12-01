package com.example.weather.repositories

import com.example.weather.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

open class BaseRepository {
    suspend fun <T> performNetworkCall(call: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO)
        {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return@withContext Resource.Success(body)
                }
                return@withContext Resource.Error("${response.code()} ${response.message()}")
            } catch (e: Exception) {
                return@withContext Resource.Error(e.message ?: e.toString())
            }
        }
    }

    suspend fun <T, A> getData(
        networkCall: suspend () -> Resource<T>,
        saveToDbQuery: suspend (T) -> Unit,
        getFromDbQuery: suspend () -> A?,
        transform: (T) -> A
    ): Resource<A> {
        return when (val fetchingResult = networkCall()) {
            is Resource.Error -> {
                val data = getFromDbQuery()
                if (data == null) Resource.Error(fetchingResult.message + "\nCannot get data from db")
                else Resource.Success(data)
            }
            is Resource.Success -> {
                saveToDbQuery(fetchingResult.data)
                Resource.Success(transform(fetchingResult.data))
            }
        }
    }
}