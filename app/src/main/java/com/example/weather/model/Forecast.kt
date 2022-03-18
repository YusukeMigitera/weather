package com.example.weather.model

data class Forecast(
    val cod: String,
    val message: Double,
    val cnt: Int,
    val list: List<ForecastBy3h>,
    val city: CityForecast
)

data class ForecastBy3h(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val sys: Sys,
    val dt_txt: String
)

data class Main(
    val temp: Double,
    val temp_mim: Double,
    val temp_max: Double,
    val pressure: Double,
    val sea_level: Double,
    val grnd_level: Double,
    val humidity: Int,
    val temp_kf: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Clouds(
    val all: Int
)

data class Wind(
    val speed: Double,
    val deg: Double
)

data class Sys(
    val pod: String
)

data class CityForecast(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Double,
    val sunset: Double
)