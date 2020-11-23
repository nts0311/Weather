package com.example.weather.model.entites.data_transfer_objects


data class OwmWeather (
	val id : Int,
	val main : String,
	val description : String,
	val icon : String
)