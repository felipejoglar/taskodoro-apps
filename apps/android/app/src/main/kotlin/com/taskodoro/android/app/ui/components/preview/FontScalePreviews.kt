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

package com.taskodoro.android.app.ui.components.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "FontScale Small",
    fontScale = 0.85f,
    showBackground = true,
)
@Preview(
    name = "FontScale Default",
    fontScale = 1.0f,
    showBackground = true,
)
@Preview(
    name = "FontScale Large",
    fontScale = 1.15f,
    showBackground = true,
)
annotation class FontScalePreviews
