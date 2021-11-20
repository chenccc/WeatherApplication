package com.james.weatherapplication.ui.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.james.weatherapplication.base.BaseViewModel
import com.james.weatherapplication.data.model.CityWeather
import com.james.weatherapplication.data.repository.WeatherRepository
import com.james.weatherapplication.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): BaseViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    val weatherEvent = SingleLiveEvent<CityWeather>()

    fun clickSearch(cityName: String) {
        if (cityName.isNotEmpty()) {
            Log.d(TAG, "Search weather for $cityName")
        }

        viewModelScope.launch {
            try {
                val result = weatherRepository.getWeatherForCity(cityName)
                Log.d(TAG, "result is $result")
                weatherEvent.postValue(result)
            } catch (ex: Exception) {
                errorMessage.postValue(ex.toString())
            }
        }
    }
}