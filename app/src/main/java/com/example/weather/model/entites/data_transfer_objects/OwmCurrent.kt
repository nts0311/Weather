package com.example.weather.model.entites.data_transfer_objects


data class OwmCurrent (
	val dt : Int,
	val sunrise : Int,
	val sunset : Int,
	val temp : Double,
	val feels_like : Double,
	val pressure : Int,
	val humidity : Int,
	val clouds : Int,
	val visibility : Int,
	val wind_speed : Double,
	val wind_deg : Int,
	val rain:OwmRain,
	val snow:OwmSnow,
	val weather : List<OwmWeather>
)

fun OwmCurrent.asDomainObject()
{

}