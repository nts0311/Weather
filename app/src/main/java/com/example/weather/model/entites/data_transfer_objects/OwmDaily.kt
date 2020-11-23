package com.example.weather.model.entites.data_transfer_objects



data class OwmDaily (
	val dt : Int,
	val sunrise : Int,
	val sunset : Int,
	val temp : OwmTemp,
	val feels_like : OwmFeelsLike,
	val pressure : Int,
	val humidity : Int,
	val wind_speed : Double,
	val wind_deg : Int,
	val weather : List<OwmWeather>,
	val clouds : Int,
	val pop : Double,
	val rain : Double,
)