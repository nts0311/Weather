package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weather.model.entites.domain_objects.Weather

@Entity(
    foreignKeys = [ForeignKey(
        entity = DailyEntity::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("dailyWeatherId"),
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = HourlyEntity::class,
            parentColumns = arrayOf("dbId"),
            childColumns = arrayOf("hourlyWeatherId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CurrentEntity::class,
            parentColumns = arrayOf("dbId"),
            childColumns = arrayOf("currentWeatherId"),
            onDelete = ForeignKey.CASCADE
        )]
)
data class WeatherEntity(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0
    var currentWeatherId: Long? = null
    var hourlyWeatherId: Long? = null
    var dailyWeatherId: Long? = null
}

fun List<WeatherEntity>.toDomainObject(): List<Weather> =
    map {
        Weather(
            it.dbId,
            it.currentWeatherId,
            it.hourlyWeatherId,
            it.dailyWeatherId,
            it.id,
            it.main,
            it.description,
            it.icon
        )
    }