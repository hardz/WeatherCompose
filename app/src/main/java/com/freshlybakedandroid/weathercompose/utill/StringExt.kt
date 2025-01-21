package com.freshlybakedandroid.weathercompose.utill

import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun String.extractDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val localDateTime = LocalDateTime.parse(this, formatter)
    return localDateTime.toLocalDate().toString()
}

fun String.getWeatherIconURL(): String {
    return "https://openweathermap.org/img/wn/${this}@2x.png"
}


fun Double.round(): String {
    return kotlin.math.round(this).toString()
}

fun Double.convertTemperature(unit: TemperatureUnit): String {
    return when (unit) {
        TemperatureUnit.KELVIN -> "${this.toInt()} K"
        TemperatureUnit.CELSIUS -> "${(this - 273.15).toInt()} °C"
        TemperatureUnit.FAHRENHEIT -> "${((this - 273.15) * 9/5 + 32).toInt()} °F"
        else -> throw IllegalArgumentException("Unknown unit: $unit")
    }
}

fun getFormattedDate(unixTimestamp: Int?): String {
    if (unixTimestamp == null){
        return ""
    }
    try {
        val date = Date(unixTimestamp * 1000L)
        val sdf = SimpleDateFormat("dd, MMM", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    } catch (e: Exception) {
        Log.e("DEBUG", "Exception getFormattedDate - ", e)
    }
    return ""
}