package com.freshlybakedandroid.weathercompose.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "pod")
    val pod: String? = null,
    @Json(name = "country")
    val country: String? = null,
    @Json(name = "sunrise")
    val sunrise: Int? = null,
    @Json(name = "sunset")
    val sunset: Int? = null,
)