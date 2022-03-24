package com.example.weather.model

data class ClimateByDay(
    val dt: Long,
    val humidity: Double,
    val pressure: Double,
    val temp: Temp,
    val wind_speed: Double
)
