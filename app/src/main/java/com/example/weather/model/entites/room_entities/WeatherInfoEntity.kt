package com.example.weather.model.entites.room_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherInfoEntity (
    val dt:Int,
)
{
    @PrimaryKey(autoGenerate = true)
    var dbId:Int = 0
}
