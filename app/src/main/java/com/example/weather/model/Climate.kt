package com.example.weather.model

data class Climate(
    val cod: String,
    val city: CityClimate,
    val message: Double,
    val list: List<ClimateByDay>
)

data class CityClimate(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class ClimateByDay(
    val dt: Long,
    val humidity: Double,
    val pressure: Double,
    val temp: Temp,
    val wind_speed: Double
)

data class Temp(
    val average: Double,
    val average_max: Double,
    val average_min: Double,
    val record_max: Double,
    val record_min: Double
)