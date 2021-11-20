package com.james.weatherapplication.data.service

import com.james.weatherapplication.data.model.CityWeather
import com.james.weatherapplication.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

// test api https://api.openweathermap.org/data/2.5/weather?q=beijing&&units=metric&appid=apikey
interface WeatherApiService {
    @GET(Constants.WEATHER)
    suspend fun getWeatherForCity(
        @Query("q") city: String,
        @Query("appid") appID: String,
        @Query("units") units:String = "metric"
    ): CityWeather
}