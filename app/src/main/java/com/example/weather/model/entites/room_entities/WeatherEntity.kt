package com.example.weather.model.entites.room_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    var dbId: Int = 0
    var baseId: Int = 0
}
