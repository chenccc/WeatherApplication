package com.james.weatherapplication.data.repository

import com.james.weatherapplication.data.model.CityWeather

interface WeatherRepository {
    suspend fun getWeatherForCity(city: String): CityWeather
    suspend fun deleteCity(city: String)
    suspend fun getAllCities(): List<CityWeather>
}