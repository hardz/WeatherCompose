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

package com.freshlybakedandroid.weathercompose.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.freshlybakedandroid.weathercompose.R
import com.freshlybakedandroid.weathercompose.ui.theme.WeatherComposeTheme
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 3000

@Composable
fun WelcomeScreen(onTimeout: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sky),
            contentDescription = null
        )
    }

    LaunchedEffect(true) {
        delay(SplashWaitTime)
        onTimeout()
    }
}

//@Preview(name = "Welcome light theme", uiMode = UI_MODE_NIGHT_YES)
//@Preview(name = "Welcome dark theme", uiMode = UI_MODE_NIGHT_NO)
@Preview
@Composable
fun WelcomeScreenPreview() {
    WeatherComposeTheme {
        WelcomeScreen( onTimeout = {})
    }
}
