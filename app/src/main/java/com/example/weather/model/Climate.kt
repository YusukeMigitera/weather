package com.example.weather.model

data class Climate constructor(
    val cod: String = "",
    val city: CityClimate = CityClimate(),
    val message: Double = 0.0,
    val list: List<ClimateByDay> = listOf()
)

data class CityClimate constructor(
    val id: Int = 0,
    val name: String = "",
    val coord: Coord = Coord(),
    val country: String = "",
)

data class Coord constructor(
    val lon: Double = 0.0,
    val lat: Double = 0.0
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