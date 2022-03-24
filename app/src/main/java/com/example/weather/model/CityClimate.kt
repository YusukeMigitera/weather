package com.example.weather.model

data class CityClimate constructor(
    val id: Int = 0,
    val name: String = "",
    val coord: Coord = Coord(),
    val country: String = "",
)
