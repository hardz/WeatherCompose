package com.freshlybakedandroid.weathercompose.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freshlybakedandroid.weathercompose.model.CurrentConditions
import com.freshlybakedandroid.weathercompose.model.DaysForeCast
import com.freshlybakedandroid.weathercompose.model.ForecastResponse
import com.freshlybakedandroid.weathercompose.model.SunriseSunset
import com.freshlybakedandroid.weathercompose.model.WeatherHeaderData
import com.freshlybakedandroid.weathercompose.model.WeatherMainData
import com.freshlybakedandroid.weathercompose.model.WeatherResponse
import com.freshlybakedandroid.weathercompose.utill.extractDate
import com.freshlybakedandroid.weathercompose.utill.getFormattedDate
import com.freshlybakedandroid.weathercompose.utill.getWeatherIconURL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherUiState = MutableLiveData<WeatherUiState>()
    val weatherUiState: LiveData<WeatherUiState> get() = _weatherUiState

    private var _weatherMainData = MutableLiveData<WeatherMainData>()
    val weatherMainData: LiveData<WeatherMainData> get() = _weatherMainData

    private var _weatherHeaderData = MutableLiveData<WeatherHeaderData>()
    val weatherHeaderData: LiveData<WeatherHeaderData> get() = _weatherHeaderData

    private var _currentConditions = MutableLiveData<CurrentConditions>()
    val currentConditions: LiveData<CurrentConditions> get() = _currentConditions

    private val _daysForeCast = MutableLiveData<List<DaysForeCast>>()
    val daysForeCast: LiveData<List<DaysForeCast>> get() = _daysForeCast

    private var _sunriseSunset = MutableLiveData<SunriseSunset>()
    val sunriseSunset: LiveData<SunriseSunset> get() = _sunriseSunset

    fun getWeatherAndForecast(lat: String, lon: String, unit: String) {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading

            val weatherResponse = async { weatherRepository.getWeatherByGeoCode(lat, lon, unit) }
            val forecastResponse = async { weatherRepository.getForecastByGeoCode(lat, lon, unit) }

            val weatherResult = weatherResponse.await()
            val forecastResult = forecastResponse.await()

            if (weatherResult.isSuccessful && forecastResult.isSuccessful) {
                weatherResult.body()?.let { weather ->
                    _weatherMainData.value = mapToWeatherMainData(weather)
                    _weatherHeaderData.value = mapToWeatherHeaderData(weather)
                    _currentConditions.value = mapToCurrentConditions(weather)
                    _sunriseSunset.value = mapToSunriseSunset(weather)

                    val daysForecast = forecastResult.body() // Assuming forecast data is in ForecastResponse
                    _daysForeCast.value = mapToDaysForeCast(daysForecast!!)
                    _weatherUiState.value = WeatherUiState.Success(weather)
                } ?: run {
                    _weatherUiState.value =
                        WeatherUiState.Error(weatherResult.code(), "No data found")
                }
            } else {
                val errorMessage =
                    weatherResult.message() ?: forecastResult.message() ?: "Unknown Error"
                _weatherUiState.value = WeatherUiState.Error(weatherResult.code(), errorMessage)
            }
        }
    }

    private fun mapToWeatherMainData(weatherResponse: WeatherResponse): WeatherMainData {
        return WeatherMainData(
            weatherType = weatherResponse.weather.firstOrNull()?.main ?: "Unknown",
            weatherDescription = weatherResponse.weather.firstOrNull()?.description ?: "Unknown",
            dayLowTemp = "Low: ${weatherResponse.main.tempMin}",
            dayHighTemp = "High: ${weatherResponse.main.tempMax}",
            currentTemp = weatherResponse.main.temp.toString(),
            feelsLikeTemp = weatherResponse.main.feelsLike.toString(),
            weatherTypeIcon = weatherResponse.weather.firstOrNull()?.icon ?: ""
        )
    }

    private fun mapToWeatherHeaderData(weatherResponse: WeatherResponse): WeatherHeaderData {
        return WeatherHeaderData(
            cityName = weatherResponse.name, todayWeather = "Today's Weather"
        )
    }

    private fun mapToCurrentConditions(weatherResponse: WeatherResponse): CurrentConditions {
        return CurrentConditions(
            windSpeed = "${weatherResponse.wind.speed} miles/hour",
            wind = "Wind",
            humidityPercentage = "${weatherResponse.main.humidity}%",
            humidity = "Humidity"
        )
    }

    private fun mapToDaysForeCast(forecastResponse: ForecastResponse): List<DaysForeCast> {

        val daysForecast = mutableListOf<DaysForeCast>()
        val distinctForecasts =
            forecastResponse.list.distinctBy {
                it.dtTxt.extractDate()
            }

        for (forecast in distinctForecasts) {
            val daysForeCast = DaysForeCast(
                day = getFormattedDate(forecast.dt),
                expectedTemp = "${forecast.main.temp}".substringBefore(".") + "°C",
                weatherType = forecast.weather.get(0).main,
                weatherTypeIcon = forecast.weather.get(0).icon.getWeatherIconURL()
            )
            daysForecast.add(daysForeCast)
        }
        Log.e("DEBUG", "daysForecast --- ${daysForecast.size}")
        return daysForecast
    }

    private fun mapToSunriseSunset(weatherResponse: WeatherResponse): SunriseSunset {
        return SunriseSunset(
            sunriseTime = getFormattedDate(weatherResponse.sys.sunrise),
            sunrise = "Sunrise",
            sunsetTime = getFormattedDate(weatherResponse.sys.sunset),
            sunset = "Sunset"
        )
    }


}