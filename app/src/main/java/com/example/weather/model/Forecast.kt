package com.example.weather.model

data class Forecast constructor(
    val cod: String = "",
    val message: Double = 0.0,
    val cnt: Int = 0,
    val list: MutableList<ForecastBy3h> = mutableListOf(),
    val city: CityForecast? = null
)
