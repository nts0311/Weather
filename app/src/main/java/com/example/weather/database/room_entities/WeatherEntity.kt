package com.example.weather.database.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weather.model.entites.domain_objects.Weather

@Entity(
    foreignKeys = [ForeignKey(
        entity = DailyEntity::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("baseId"),
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = HourlyEntity::class,
            parentColumns = arrayOf("dbId"),
            childColumns = arrayOf("baseId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CurrentEntity::class,
            parentColumns = arrayOf("dbId"),
            childColumns = arrayOf("baseId"),
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
    var baseId: Long = 0
}

fun List<WeatherEntity>.toDomainObject(): List<Weather> =
    map { Weather(it.dbId, it.baseId, it.id, it.main, it.description, it.icon) }