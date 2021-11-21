package com.james.weatherapplication.ui.drawer

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.james.weatherapplication.base.BaseViewModel
import com.james.weatherapplication.data.repository.WeatherRepository
import com.james.weatherapplication.ui.home.HomeViewModel
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

    fun getCities() {
        viewModelScope.launch {
            try {
                val result = weatherRepository.getAllCities()
                Log.d(TAG, "result is $result")
            } catch (ex: Exception) {
                errorMessage.postValue(ex.toString())
            }
        }
    }
}