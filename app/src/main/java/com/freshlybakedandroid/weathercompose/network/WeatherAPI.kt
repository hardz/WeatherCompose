package com.freshlybakedandroid.weathercompose.network

import com.freshlybakedandroid.weathercompose.BuildConfig
import com.freshlybakedandroid.weathercompose.model.ForecastResponse
import com.freshlybakedandroid.weathercompose.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    //Current weather data
    //https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid={API key}&units=imperial
    @GET("weather?appid=${BuildConfig.OPEN_WEATHER_API_KEY}")
    suspend fun getWeatherByGeoCode(@Query("lat") lat :String, @Query("lon") lon :String, @Query("units") unit :String) : Response<WeatherResponse>


    //5 day weather forecast
    //https://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid={API key}&units=imperial
    @GET("forecast?appid=${BuildConfig.OPEN_WEATHER_API_KEY}")
    suspend fun getForecastByGeoCode(@Query("lat") lat :String, @Query("lon") lon :String, @Query("units") unit :String) : Response<ForecastResponse>

}