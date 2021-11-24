package com.james.weatherapplication.ui.drawer

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
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
class DrawerViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): BaseViewModel(){
    companion object {
        private const val TAG = "DrawerViewModel"
    }

    private val _cityWeatherList = SingleLiveEvent<List<CityWeather>>()
    val cityWeatherList: LiveData<List<CityWeather>> = _cityWeatherList

    private val _selectWeather = SingleLiveEvent<String>()
    val selectWeather: LiveData<String> = _selectWeather

    fun getCities() {
        viewModelScope.launch {
            try {
                val result = weatherRepository.getAllCities()
                _cityWeatherList.postValue(result)
            } catch (ex: Exception) {
                errorMessage.postValue(ex.message.toString())
            }
        }
    }

    // select a city from history
    fun selectCity(cityWeather: CityWeather) {
        _selectWeather.postValue(cityWeather.name)
    }

    // delete a city from history
    fun deleteCity(city: String) {
        viewModelScope.launch {
            try {
                weatherRepository.deleteCity(city)
                getCities()
            } catch (ex: Exception) {
                errorMessage.postValue(ex.message.toString())
            }
        }
    }
}