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

import androidx.compose.ui.graphics.Color

// Light Theme Colors
val ForegroundDefaultLight = Color(red = 31, green = 35, blue = 40)
val BackgroundDefaultLight = Color(red = 255, green = 255, blue = 255)

val GreenAccentLight = Color(red = 26, green = 127, blue = 55)
val GreenBackgroundLight = Color(red = 218, green = 251, blue = 225)
val GreenBackgroundEmphasisLight = Color(red = 31, green = 136, blue = 61)
val GreenBorderLight = Color(red = 74, green = 194, blue = 107).copy(alpha = 0.4f)
val GreenBorderEmphasisLight = GreenBackgroundEmphasisLight

val BrownAccentLight = Color(red = 154, green = 103, blue = 0)
val BrownBackgroundLight = Color(red = 255, green = 248, blue = 197)
val BrownBackgroundEmphasisLight = Color(red = 154, green = 103, blue = 0)
val BrownBorderLight = Color(red = 212, green = 167, blue = 44).copy(alpha = 0.4f)
val BrownBorderEmphasisLight = BrownBackgroundEmphasisLight

val PinkAccentLight = Color(red = 191, green = 57, blue = 137)
val PinkBackgroundLight = Color(red = 255, green = 239, blue = 247)
val PinkBackgroundEmphasisLight = Color(red = 191, green = 57, blue = 137)
val PinkBorderLight = Color(red = 255, green = 128, blue = 200).copy(alpha = 0.4f)
val PinkBorderEmphasisLight = PinkBackgroundEmphasisLight

val RedAccentLight = Color(red = 209, green = 36, blue = 47)
val RedBackgroundLight = Color(red = 255, green = 235, blue = 233)
val RedBackgroundEmphasisLight = Color(red = 207, green = 34, blue = 46)
val RedBorderLight = Color(red = 255, green = 129, blue = 130).copy(alpha = 0.4f)
val RedBorderEmphasisLight = RedBackgroundEmphasisLight

// Dark Theme Colors
val ForegroundDefaultDark = Color(red = 230, green = 237, blue = 243)
val BackgroundDefaultDark = Color(red = 13, green = 17, blue = 23)

val GreenAccentDark = Color(63, 185, 80)
val GreenBackgroundDark = Color(red = 46, green = 160, blue = 67).copy(alpha = 0.15f)
val GreenBackgroundEmphasisDark = Color(red = 55, green = 134, blue = 54)
val GreenBorderDark = Color(red = 46, green = 160, blue = 67).copy(alpha = 0.4f)
val GreenBorderEmphasisDark = GreenBackgroundEmphasisDark

val BrownAccentDark = Color(210, 153, 34)
val BrownBackgroundDark = Color(187, 128, 9).copy(alpha = 0.15f)
val BrownBackgroundEmphasisDark = Color(158, 106, 3)
val BrownBorderDark = Color(187, 128, 9).copy(alpha = 0.4f)
val BrownBorderEmphasisDark = BrownBackgroundEmphasisDark

val PinkAccentDark = Color(219, 97, 162)
val PinkBackgroundDark = Color(219, 97, 162).copy(alpha = 0.1f)
val PinkBackgroundEmphasisDark = Color(191, 75, 138)
val PinkBorderDark = Color(219, 97, 162).copy(alpha = 0.4f)
val PinkBorderEmphasisDark = PinkBackgroundEmphasisDark

val RedAccentDark = Color(248, 81, 73)
val RedBackgroundDark = Color(248, 81, 73).copy(alpha = 0.1f)
val RedBackgroundEmphasisDark = Color(218, 54, 51)
val RedBorderDark = Color(248, 81, 73).copy(alpha = 0.4f)
val RedBorderEmphasisDark = RedBackgroundEmphasisDark
