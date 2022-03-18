package com.example.weather.model

data class Forecast(
    val cod: String,
    val city: City,
    val message: Double,
    val list: List<ForecastBy3h>
)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class ForecastBy3h(
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