package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weather.model.entites.domain_objects.AirQualityIndex

@Entity(
    foreignKeys = [ForeignKey(
        entity = WeatherInfoEntity::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("weatherInfoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class AirQualityEntity(val aqius: Int) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0
    var weatherInfoId: Long = 1
}

fun AirQualityEntity.asDomainObject(): AirQualityIndex = AirQualityIndex(dbId, weatherInfoId, aqius)