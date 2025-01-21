/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.freshlybakedandroid.weathercompose.weather

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freshlybakedandroid.weathercompose.R
import com.freshlybakedandroid.weathercompose.model.CurrentConditions
import com.freshlybakedandroid.weathercompose.model.DaysForeCast
import com.freshlybakedandroid.weathercompose.model.SunriseSunset
import com.freshlybakedandroid.weathercompose.model.WeatherHeaderData
import com.freshlybakedandroid.weathercompose.model.WeatherMainData
import com.freshlybakedandroid.weathercompose.ui.theme.WeatherComposeTheme
import com.freshlybakedandroid.weathercompose.utill.TemperatureUnitManager


@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    val weatherUiState by viewModel.weatherUiState.observeAsState(WeatherUiState.Loading)
    val weatherHeaderData by viewModel.weatherHeaderData.observeAsState()
    val weatherMainData by viewModel.weatherMainData.observeAsState()
    val currentConditionsData by viewModel.currentConditions.observeAsState()
    val daysForeCastData by viewModel.daysForeCast.observeAsState()
    val sunriseSunsetData by viewModel.sunriseSunset.observeAsState()

    LaunchedEffect(Unit) {
        //TODO() : Make unit dynamic with state
        val unit = TemperatureUnitManager.getUnit().toString()
        Log.e("DEBUG", "----- unittt  $unit")
        viewModel.getWeatherAndForecast("22.2782303", "73.1813446", TemperatureUnitManager.getUnit().toString()) //default, metric, imperial
//        viewModel.getWeatherByGeoCode("37.7749", "-122.4194", "metric") // Example lat/lon for San Francisco
    }

    when(weatherUiState){
        WeatherUiState.Loading -> {
            Log.e("DEBUG", "--- Loading")
            Loader()
        }
        is WeatherUiState.Success -> {
            Log.e("DEBUG", "--- Success --- ${weatherMainData?.weatherType}")
            Scaffold { innerPadding ->
                Surface(modifier.padding(innerPadding), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())) {
                        Header(weatherHeaderData!!)
                        Spacer()
                        WeatherMainDetails(weatherMainData!!)
                        CurrentConditions(currentConditionsData!!)
                        SevenDaysForeCast(daysForeCastData!!)
                        SunriseSunset(sunriseSunsetData!!)
                        Footer()
                    }
                }
            }

        }
        is WeatherUiState.Error -> {
            Log.e("DEBUG", "--- Error")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "message", color = MaterialTheme.colorScheme.error)
            }
        }
    }

}

@Composable
fun Spacer() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Loader() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun Header(weatherHeaderData: WeatherHeaderData) {
    Row( modifier = Modifier
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = weatherHeaderData.todayWeather, style = MaterialTheme.typography.titleSmall)
            Text(text = weatherHeaderData.cityName, style = MaterialTheme.typography.titleLarge)
        }

        Icon(
            Icons.Rounded.Settings, contentDescription = "setting",
            Modifier.clickable { Log.e("DEBUG", "Testing Icon Click") }
        )
    }
}

@Composable
fun WeatherMainDetails(weatherMainData: WeatherMainData) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.Start) {
                Text(text = weatherMainData.currentTemp, style = MaterialTheme.typography.displayMedium)

                Text(text = "${weatherMainData.dayHighTemp} - ${weatherMainData.dayLowTemp}", style = MaterialTheme.typography.titleMedium)
            }
            Image(
                painter = painterResource(id = R.drawable.cloudweather),
                contentDescription = "cloudweather",
                modifier = Modifier.size(70.dp),
            )
            Column(modifier = Modifier
                .padding(8.dp)
                .weight(1f), horizontalAlignment = Alignment.End) {
                Text(text = weatherMainData.weatherType, style = MaterialTheme.typography.titleMedium)
                Text(text = weatherMainData.feelsLikeTemp, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
fun CurrentConditions(currentConditionsData: CurrentConditions) {
    SectionLabel("Current Condition")
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConditionDetails(
                currentConditionsData.wind, currentConditionsData.windSpeed, modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start
            )
            Spacer()
            ConditionDetails(
                currentConditionsData.humidity, currentConditionsData.humidityPercentage, modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End
            )
        }
    }
}

@Composable
fun ConditionDetails(
    label: String,
    details: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = modifier, horizontalAlignment = horizontalAlignment
    ) {
        Text(text = label, style = MaterialTheme.typography.titleMedium)
        Text(text = details, style = MaterialTheme.typography.titleLarge)
    }

}

@Composable
fun SectionLabel(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 8.dp)
    )
}

@Composable
fun BodyText(label: String, modifier: Modifier) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}

@Composable
fun SevenDaysForeCast(daysForeCastData: List<DaysForeCast>) {
    SectionLabel("7-day forecast")
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        for (daysForeCast in daysForeCastData) {
            DaysForeCast(daysForeCast)
        }
    }

}

@Composable
fun DaysForeCast(daysForeCast: DaysForeCast) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyText(daysForeCast.day, modifier = Modifier.weight(.4f))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,  modifier = Modifier.weight(.6f)){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "cloudweather",
                modifier = Modifier.size(32.dp)
            )
            BodyText(daysForeCast.expectedTemp,
                Modifier
                    .padding(start = 8.dp)
                    .weight(1f))
            BodyText(daysForeCast.weatherType,
                Modifier
                    .padding(start = 8.dp)
                    .weight(1f))
        }
    }
}

@Composable
fun SunriseSunset(sunriseSunsetData: SunriseSunset) {
    SectionLabel("Sunrise & sunset")
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConditionDetails(
                sunriseSunsetData.sunrise, sunriseSunsetData.sunriseTime, modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start
            )
            Spacer()
            ConditionDetails(
                sunriseSunsetData.sunset, sunriseSunsetData.sunsetTime, modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Footer() {

    Spacer()
    Spacer()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Current Weather", style = MaterialTheme.typography.titleSmall)
        Text(text = "Powered By OpenWeatherMap API", style = MaterialTheme.typography.titleSmall)
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    val sampleData = WeatherHeaderData(
        todayWeather = "Sunny",
        cityName = "Halifax"
    )
    Header(weatherHeaderData = sampleData)
}
