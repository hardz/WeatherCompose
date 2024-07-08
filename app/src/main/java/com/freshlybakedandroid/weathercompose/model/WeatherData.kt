package com.freshlybakedandroid.weathercompose.model


data class WeatherMainData(
    val weatherType: String,
    val weatherDescription: String,
    val dayLowTemp: String,
    val dayHighTemp: String,
    val currentTemp: String,
    val feelsLikeTemp: String,
    val weatherTypeIcon: String,
)


data class WeatherHeaderData(
    val cityName: String,
    val todayWeather: String
)

data class CurrentConditions(
    val wind: String,
    val windSpeed: String,
    val humidity: String,
    val humidityPercentage: String,
)

data class DaysForeCast(
    val day: String,
    val expectedTemp: String,
    val weatherType: String,
    val weatherTypeIcon: String,
)

data class SunriseSunset(
    val sunrise: String,
    val sunriseTime: String,
    val sunset: String,
    val sunsetTime: String,
)



