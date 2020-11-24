package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(var lat:Double,
var long : Double,
var name : String)
{
    @PrimaryKey(autoGenerate = true)
    var dbId : Long = 0
}
