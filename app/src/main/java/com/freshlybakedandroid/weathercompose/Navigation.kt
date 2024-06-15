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

package com.freshlybakedandroid.weathercompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freshlybakedandroid.weathercompose.Destinations.SETTING_ROUTE
import com.freshlybakedandroid.weathercompose.Destinations.WEATHER_ROUTE
import com.freshlybakedandroid.weathercompose.Destinations.WELCOME_ROUTE
import com.freshlybakedandroid.weathercompose.weather.WeatherScreen
import com.freshlybakedandroid.weathercompose.welcome.WelcomeScreen

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val SETTING_ROUTE = "setting"
    const val WEATHER_ROUTE = "weather"
}

@Composable
fun WeatherNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = WELCOME_ROUTE) {
        composable(WELCOME_ROUTE) {
            WelcomeScreen {
                navController.navigate(WEATHER_ROUTE) {
                    popUpTo(WELCOME_ROUTE) { inclusive = true }
                }
            }
        }
        composable(WEATHER_ROUTE) {
            WeatherScreen()
        }
        composable(SETTING_ROUTE) {
            WeatherScreen()
        }
    }
}
