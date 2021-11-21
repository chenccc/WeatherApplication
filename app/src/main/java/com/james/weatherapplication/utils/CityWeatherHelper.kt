package com.james.weatherapplication.utils

import com.james.weatherapplication.data.model.*

object CityWeatherHelper {
    fun createEmptyCityWeather() = CityWeather(
        coord = Coord(
            lat = 0.0,
            lon = 0.0
        ),
        weather = listOf(
            Weather(
                id = 0,
                main = "",
                description = "",
                icon = ""
            )
        ),
        base = "",
        main = Main(
            temp = 0.0,
            feels_like = 0.0,
            temp_max = 0.0,
            temp_min = 0.0,
            pressure = 0,
            humidity = 0,
            sea_level = 0,
            grnd_level = 0
        ),
        visibility = 0,
        wind = Wind(
            speed = 0.0,
            deg = 0,
            gust = 0.0
        ),
        clouds = Clouds(
            all = 0
        ),
        dt = 0,
        sys = Sys(
            type = 0,
            id = 0,
            country = "",
            sunrise = 0,
            sunset = 0
        ),
        timezone = 0,
        id = 0,
        name = "",
        cod = 0
    )
}