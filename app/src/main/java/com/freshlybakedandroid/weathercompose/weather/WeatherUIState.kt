package com.freshlybakedandroid.weathercompose.weather

import com.freshlybakedandroid.weathercompose.model.WeatherResponse

/**
 * A sealed hierarchy for the Weather state.
 */
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weatherResponse: WeatherResponse) : WeatherUiState()
    data class Error(val code: Int, val message: String) : WeatherUiState()
}
