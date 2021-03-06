package com.james.weatherapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
): Parcelable