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

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.taskodoro.android.R

private val ralewayFamily = FontFamily(
    Font(R.font.raleway_light, FontWeight.Light),
    Font(R.font.raleway_regular, FontWeight.Normal),
    Font(R.font.raleway_medium, FontWeight.Medium),
    Font(R.font.raleway_bold, FontWeight.Bold),
)

private val defaultTypography = Typography()

val typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = ralewayFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = ralewayFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = ralewayFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = ralewayFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = ralewayFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = ralewayFamily),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = ralewayFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = ralewayFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = ralewayFamily),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = ralewayFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = ralewayFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = ralewayFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = ralewayFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = ralewayFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = ralewayFamily),
)
