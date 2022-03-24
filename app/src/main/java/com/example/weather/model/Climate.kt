package com.example.weather.model

data class Climate constructor(
    val cod: String = "",
    val city: CityClimate = CityClimate(),
    val message: Double = 0.0,
    val list: List<ClimateByDay> = listOf()
)
