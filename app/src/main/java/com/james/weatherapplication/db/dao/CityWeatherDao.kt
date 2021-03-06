package com.james.weatherapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.james.weatherapplication.data.model.CityWeather

@Dao
interface CityWeatherDao {
    @Query("SELECT * FROM CityWeather WHERE upper(name) = :city")
    suspend fun getWeatherForCity(city: String): CityWeather?

    @Query("DELETE FROM CityWeather WHERE name = :city")
    suspend fun clearCity(city: String)

    @Query("SELECT * FROM CityWeather ORDER BY lastAccessTime DESC")
    suspend fun getAllRequestCities(): List<CityWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(cityWeather: CityWeather)
}