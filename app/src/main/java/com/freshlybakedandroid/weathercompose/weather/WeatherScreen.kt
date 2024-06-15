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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freshlybakedandroid.weathercompose.R
import com.freshlybakedandroid.weathercompose.ui.theme.WeatherComposeTheme


@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
            Header("Vadodara")
            WeatherMainDetails()
            CurrentConditions()
            SevenDaysForeCast()
            SunriseSunset()
            Footer()
        }
    }
}


@Composable
fun Header(cityName: String) {
    Row( modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Today's Weather", style = MaterialTheme.typography.titleSmall)
            Text(text = cityName, style = MaterialTheme.typography.titleLarge)
        }

        Icon(
            Icons.Rounded.Settings, contentDescription = "setting",
            Modifier
                .clickable {
                    Log.e("DEBUG", "Testing Icon Click")
                }
                .padding(8.dp))
    }
}

@Composable
fun WeatherMainDetails() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.Start) {
                Text(text = "37*", style = MaterialTheme.typography.displayMedium)
                Text(text = "High: 39* * Low: 29*", style = MaterialTheme.typography.titleMedium)
            }
            Image(
                painter = painterResource(id = R.drawable.cloudweather),
                contentDescription = "cloudweather",
                modifier = Modifier.size(70.dp),
            )
            Column(modifier = Modifier.padding(8.dp).weight(1f), horizontalAlignment = Alignment.End) {
                Text(text = "smoke", style = MaterialTheme.typography.titleMedium)
                Text(text = "Feels like 39*", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
fun CurrentConditions() {
    SectionLabel("Current Condition")
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConditionDetails(
            "Wind", "28 km/h", modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start
        )
        Spacer(Modifier.width(16.dp))
        ConditionDetails(
            "Humidity", "73%", modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End
        )
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
        modifier = Modifier.padding(vertical = 16.dp)
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
fun SevenDaysForeCast() {
    SectionLabel("7-day forecast")
    for (i in 1..7) {
        DaysForeCast()
    }
}

@Composable
fun DaysForeCast() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyText("12, Jun", modifier = Modifier.weight(.4f))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,  modifier = Modifier.weight(.6f)){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "cloudweather",
                modifier = Modifier.size(32.dp)
            )
            BodyText("35*", Modifier.padding(start = 8.dp))
            BodyText("Clouds", Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun SunriseSunset() {
    SectionLabel("Sunrise & sunset")
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConditionDetails(
            "Sunrise", "5:53 am", modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start
        )
        Spacer(Modifier.width(16.dp))
        ConditionDetails(
            "Sunset", "7:22 pm", modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End
        )
    }
}

@Composable
fun Footer() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Current Weather", style = MaterialTheme.typography.titleSmall)
        Text(text = "Powered By OpenWeatherMap API", style = MaterialTheme.typography.titleSmall)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherComposeTheme {
        WeatherScreen(Modifier.fillMaxSize())
    }
}
