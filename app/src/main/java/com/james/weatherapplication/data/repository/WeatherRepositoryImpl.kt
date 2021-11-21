package com.james.weatherapplication.data.repository

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.james.weatherapplication.R
import com.james.weatherapplication.data.model.CityWeather
import com.james.weatherapplication.data.service.WeatherApiService
import com.james.weatherapplication.db.AppDB
import com.james.weatherapplication.utils.Constants
import com.james.weatherapplication.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: WeatherApiService,
    private val db: AppDB,
    @ApplicationContext private val appContext: Context
): WeatherRepository{
    override suspend fun getWeatherForCity(city: String): CityWeather {
        // retrieve from db
        db.cityWeatherDao().getWeatherForCity(city.uppercase())?.let {
            if (!NetworkUtils.isNetworkConnected(appContext)) {
                insertWeatherToDB(it.copy(lastAccessTime = System.currentTimeMillis()))
                return it
            }
        }

        // retrieve from api
        return service.getWeatherForCity(
            city = city,
            appID = appContext.resources.getString(R.string.api_key)
        ).apply {
            if (cod == Constants.GOOD_RESPONSE) {
                insertWeatherToDB(this.copy(lastAccessTime = System.currentTimeMillis()))
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    suspend fun insertWeatherToDB(weather: CityWeather) {
        db.cityWeatherDao().insertWeather(weather)
    }

    override suspend fun deleteCity(city: String) {
        db.cityWeatherDao().clearCity(city)
    }

    override suspend fun getAllCities(): List<CityWeather> =
        db.cityWeatherDao().getAllRequestCities()
}