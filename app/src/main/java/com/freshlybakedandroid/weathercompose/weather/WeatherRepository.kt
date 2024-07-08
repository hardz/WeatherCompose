package com.freshlybakedandroid.weathercompose.weather

import com.freshlybakedandroid.weathercompose.model.ForecastResponse
import com.freshlybakedandroid.weathercompose.model.WeatherResponse
import com.freshlybakedandroid.weathercompose.network.WeatherAPI
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherAPI: WeatherAPI
) {
    suspend fun getWeatherByGeoCode(lat: String, lon: String, unit: String): Response<WeatherResponse> {
        return  weatherAPI.getWeatherByGeoCode(lat, lon, unit)
    }

    suspend fun getForecastByGeoCode(lat: String, lon: String, unit: String): Response<ForecastResponse> {
        return  weatherAPI.getForecastByGeoCode(lat, lon, unit)
    }
}
