package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weather.model.entites.domain_objects.UvIndex

@Entity(
    foreignKeys = [ForeignKey(
        entity = WeatherInfoEntity::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("weatherInfoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class UvIndexEntity(
    val uv: Double,
    val uvMax: Double
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0
    var weatherInfoId: Long = 1
}

fun UvIndexEntity.asDomainObject(): UvIndex = UvIndex(dbId, weatherInfoId, uv, uvMax)