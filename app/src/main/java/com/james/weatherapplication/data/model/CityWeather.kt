package com.james.weatherapplication.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "CityWeather")
data class CityWeather(
    val base: String,

    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    val cod: Int,

    @Embedded(prefix = "coord")
    val coord: Coord,
    val dt: Int,
    val id: Int,

    @Embedded(prefix = "main")
    val main: Main,

    @PrimaryKey
    val name: String,

    @Embedded(prefix = "sys")
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,

    val weather: List<Weather>,

    @Embedded(prefix = "wind")
    val wind: Wind,

    val lastAccessTime: Long
): Parcelable