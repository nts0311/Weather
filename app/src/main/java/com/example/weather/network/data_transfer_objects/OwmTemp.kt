package com.example.weather.network.data_transfer_objects


data class OwmTemp (
	val day : Double,
	val min : Double,
	val max : Double,
	val night : Double,
	val eve : Double,
	val morn : Double
)