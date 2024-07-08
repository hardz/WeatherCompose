package com.freshlybakedandroid.weathercompose.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainX(
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "temp_max")
    val tempMax: Double,
    @Json(name = "temp_min")
    val tempMin: Double
)