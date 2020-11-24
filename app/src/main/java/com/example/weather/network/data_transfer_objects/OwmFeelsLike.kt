package com.example.weather.network.data_transfer_objects


data class OwmFeelsLike (
	val day : Double,
	val night : Double,
	val eve : Double,
	val morn : Double
)