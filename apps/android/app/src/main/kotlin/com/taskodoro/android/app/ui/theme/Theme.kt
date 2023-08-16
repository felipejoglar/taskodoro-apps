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

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColors = darkColorScheme(
    primary = LimeGreen40,
    onPrimary = LimeGreen100,
    primaryContainer = LimeGreen90,
    onPrimaryContainer = LimeGreen10,
    secondary = LimeGreen40,
    onSecondary = LimeGreen100,
    secondaryContainer = LimeGreen90,
    onSecondaryContainer = LimeGreen10,
    tertiary = LimeGreen40,
    onTertiary = LimeGreen100,
    tertiaryContainer = LimeGreen90,
    onTertiaryContainer = LimeGreen10,
    surfaceTint = Color.Transparent,
)

private val LightColors = lightColorScheme(
    primary = LimeGreen40,
    onPrimary = LimeGreen100,
    primaryContainer = LimeGreen90,
    onPrimaryContainer = LimeGreen10,
    secondary = LimeGreen40,
    onSecondary = LimeGreen100,
    secondaryContainer = LimeGreen90,
    onSecondaryContainer = LimeGreen10,
    tertiary = LimeGreen40,
    onTertiary = LimeGreen100,
    tertiaryContainer = LimeGreen90,
    onTertiaryContainer = LimeGreen10,
    surfaceTint = Color.Transparent,
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colors = when {
        useDynamicColors && isAndroid12OrLater && useDarkTheme -> dynamicDarkColorScheme(
            LocalContext.current,
        )

        useDynamicColors && isAndroid12OrLater && !useDarkTheme -> dynamicLightColorScheme(
            LocalContext.current,
        )

        useDarkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}

private val isAndroid12OrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
