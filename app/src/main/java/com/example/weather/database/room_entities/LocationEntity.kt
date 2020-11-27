package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    var lat: Double,
    var long: Double,
) {
    constructor() : this(0.0,0.0)

    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0

    var name: String = ""
}
