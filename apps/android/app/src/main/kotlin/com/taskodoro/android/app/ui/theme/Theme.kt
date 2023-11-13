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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taskodoro.android.app.ui.components.preview.ComponentPreviews

private val LightColors = lightColorScheme(
    primary = DarkCyanAccent,
    onPrimary = ForegroundDefaultLight,
    primaryContainer = DarkCyanBackground,
    onPrimaryContainer = DarkCyanAccent,
    secondary = TurquoiseAccent,
    onSecondary = ForegroundDefaultLight,
    secondaryContainer = TurquoiseBackground,
    onSecondaryContainer = TurquoiseAccent,
    tertiary = AureolinAccent,
    onTertiary = ForegroundDefaultLight,
    tertiaryContainer = AureolinBackground,
    onTertiaryContainer = AureolinAccent,
    error = PoppyAccent,
    onError = ForegroundDefaultLight,
    errorContainer = PoppyBackground,
    onErrorContainer = PoppyAccent,
    background = BackgroundDefaultLight,
    onBackground = ForegroundDefaultLight,
    surface = BackgroundDefaultLight,
    onSurface = ForegroundDefaultLight,
    surfaceTint = Color.Transparent,
    surfaceVariant = BackgroundSubtleLight,
    onSurfaceVariant = ForegroundSubtleLight,
)

private val DarkColors = darkColorScheme(
    primary = DarkCyanAccent,
    onPrimary = ForegroundDefaultDark,
    primaryContainer = DarkCyanAccent,
    onPrimaryContainer = DarkCyanAccent,
    secondary = TurquoiseAccent,
    onSecondary = ForegroundDefaultDark,
    secondaryContainer = TurquoiseBackground,
    onSecondaryContainer = TurquoiseAccent,
    tertiary = AureolinAccent,
    onTertiary = ForegroundDefaultDark,
    tertiaryContainer = AureolinBackground,
    onTertiaryContainer = AureolinAccent,
    error = PoppyAccent,
    onError = ForegroundDefaultDark,
    errorContainer = PoppyBackground,
    onErrorContainer = PoppyAccent,
    background = BackgroundDefaultDark,
    onBackground = ForegroundDefaultDark,
    surface = BackgroundDefaultDark,
    onSurface = ForegroundDefaultDark,
    surfaceTint = Color.Transparent,
    surfaceVariant = BackgroundSubtleDark,
    onSurfaceVariant = ForegroundSubtleDark,
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit,
) {
    val dynamicColorsEnabled = useDynamicColors && isAndroid12OrLater
    val colors = when {
        dynamicColorsEnabled && useDarkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColorsEnabled && !useDarkTheme -> dynamicLightColorScheme(LocalContext.current)
        useDarkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = typography,
    )
}

private val isAndroid12OrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@ComponentPreviews
@Composable
private fun ColorPreview() {
    AppTheme(
        useDynamicColors = false,
    ) {
        Column(
            Modifier.background(MaterialTheme.colorScheme.background),
        ) {
            ColoredPreview(
                color = MaterialTheme.colorScheme.primary,
                colorContainer = MaterialTheme.colorScheme.primaryContainer,
                onColorContainer = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            ColoredPreview(
                color = MaterialTheme.colorScheme.secondary,
                colorContainer = MaterialTheme.colorScheme.secondaryContainer,
                onColorContainer = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            ColoredPreview(
                color = MaterialTheme.colorScheme.tertiary,
                colorContainer = MaterialTheme.colorScheme.tertiaryContainer,
                onColorContainer = MaterialTheme.colorScheme.onTertiaryContainer,
            )
            ColoredPreview(
                color = MaterialTheme.colorScheme.error,
                colorContainer = MaterialTheme.colorScheme.errorContainer,
                onColorContainer = MaterialTheme.colorScheme.onErrorContainer,
            )
        }
    }
}

@Composable
private fun ColoredPreview(
    color: Color,
    colorContainer: Color,
    onColorContainer: Color,
) {
    Column {
        Text(
            text = "This is a Theme",
            style = MaterialTheme.typography.headlineMedium,
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .background(colorContainer)
                .border(
                    width = 2.dp,
                    color = onColorContainer,
                    shape = RoundedCornerShape(16.dp),
                )
                .clip(RoundedCornerShape(16.dp)),
        ) {
            Text(
                text = "This is a Theme",
                style = MaterialTheme.typography.headlineMedium,
                color = onColorContainer,
                modifier = Modifier.padding(all = 16.dp),
            )
        }
    }
}
