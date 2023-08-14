/*
 *    Copyright 2023 Green Peaks Studio
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.taskodoro.android.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = Frost02_600,
    onPrimary = SnowStorm01,
    primaryContainer = Frost02_800,
    onPrimaryContainer = Frost02_300,
    secondary = Frost01_600,
    onSecondary = SnowStorm01,
    secondaryContainer = Frost01_800,
    onSecondaryContainer = Frost01_300,
    tertiary = Frost04_800,
    onTertiary = SnowStorm01,
    tertiaryContainer = Frost04_1000,
    onTertiaryContainer = Frost04_500,
    error = AuroraRed800,
    errorContainer = AuroraRed1000,
    onError = SnowStorm01,
    onErrorContainer = AuroraRed500,
    background = PolarNight01,
    onBackground = SnowStorm01,
    surface = PolarNight02,
    onSurface = SnowStorm02,
    inverseOnSurface = PolarNight02,
    inverseSurface = SnowStorm02,
    inversePrimary = Frost02_600,
    outline = SnowStorm03,
    outlineVariant = PolarNight04,
)

private val LightColors = lightColorScheme(
    primary = Frost02_600,
    onPrimary = PolarNight01,
    primaryContainer = Frost02_300,
    onPrimaryContainer = Frost02_800,
    secondary = Frost01_600,
    onSecondary = PolarNight01,
    secondaryContainer = Frost01_300,
    onSecondaryContainer = Frost01_800,
    tertiary = Frost04_800,
    onTertiary = PolarNight01,
    tertiaryContainer = Frost04_500,
    onTertiaryContainer = Frost04_1000,
    error = AuroraRed800,
    errorContainer = AuroraRed500,
    onError = PolarNight01,
    onErrorContainer = AuroraRed1000,
    background = SnowStorm01,
    onBackground = PolarNight01,
    surface = SnowStorm02,
    onSurface = PolarNight02,
    inverseOnSurface = SnowStorm02,
    inverseSurface = PolarNight02,
    inversePrimary = Frost02_600,
    outline = PolarNight04,
    outlineVariant = SnowStorm03,
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}
