package com.james.weatherapplication.utils
import androidx.room.TypeConverter
import com.james.weatherapplication.data.model.Weather
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson


class WeatherListConverter {
    @TypeConverter
    fun stringToWeatherList(data: String?): List<Weather> {
        if (data == null) {
            return listOf()
        }
        val listType = object : TypeToken<List<Weather?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun weatherListToString(someObjects: List<Weather?>?): String {
        return Gson().toJson(someObjects)
    }
}