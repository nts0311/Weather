package com.example.weather.model.entites.data_transfer_objects


data class OwmBaseResponse (
    val dt:Int,
    val current : OwmCurrent,
    val hourly : List<OwmHourly>,
    val daily : List<OwmDaily>
)