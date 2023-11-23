/*
 *    Copyright 2023 Felipe Joglar
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

package com.taskodoro.android.app.ui.components.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers

@Preview(
    name = "Red Dominated",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
)
@Preview(
    name = "Blue Dominated",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
)
@Preview(
    name = "Green Dominated",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
)
@Preview(
    name = "Yellow Dominated",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE,
)
annotation class DynamicColorsPreviews
