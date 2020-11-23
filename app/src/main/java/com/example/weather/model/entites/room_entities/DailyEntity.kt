package com.example.weather.model.entites.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
	foreignKeys = [ForeignKey(
		entity = WeatherInfoEntity::class,
		parentColumns = arrayOf("dbId"),
		childColumns = arrayOf("weatherInfoId"),
		onDelete = ForeignKey.CASCADE
	)]
)
data class DailyEntity (
	val dt : Int,
	val sunrise : Int,
	val sunset : Int,
	val dayTemp : Double,
	val minTemp : Double,
	val maxTemp : Double,
	val nightTemp : Double,
	val eveTemp : Double,
	val mornTemp : Double,
	val dayFeelsLike : Double,
	val nightFeelsLike : Double,
	val eveFeelsLike : Double,
	val mornFeelsLike : Double,
	val pressure : Int,
	val humidity : Int,
	val windSpeed : Double,
	val windDeg : Int,
	val clouds : Int,
	val rain : Double,
	val snow:Double,
	val pop : Int,
)
{
	@PrimaryKey(autoGenerate = true)
	var dbId: Int = 0
	var weatherInfoId: Int = 0
}