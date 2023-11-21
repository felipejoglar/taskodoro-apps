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

package com.taskodoro.android.app.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.History: ImageVector
    get() {
        if (history != null) {
            return history!!
        }
        history = ImageVector.Builder(
            name = "History",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f,
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(21.458f, 19.417f)
                lineToRelative(4.542f, 4.5f)
                quadToRelative(0.375f, 0.375f, 0.375f, 0.937f)
                quadToRelative(0f, 0.563f, -0.375f, 0.938f)
                quadToRelative(-0.417f, 0.416f, -0.938f, 0.416f)
                quadToRelative(-0.52f, 0f, -0.937f, -0.416f)
                lineToRelative(-4.917f, -4.917f)
                quadToRelative(-0.208f, -0.208f, -0.312f, -0.437f)
                quadToRelative(-0.104f, -0.23f, -0.104f, -0.521f)
                verticalLineToRelative(-6.875f)
                quadToRelative(0f, -0.542f, 0.396f, -0.938f)
                quadToRelative(0.395f, -0.396f, 0.937f, -0.396f)
                reflectiveQuadToRelative(0.937f, 0.396f)
                quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                close()
                moveTo(19.875f, 34.75f)
                quadToRelative(-5.5f, 0f, -9.458f, -3.438f)
                quadToRelative(-3.959f, -3.437f, -4.917f, -8.729f)
                quadToRelative(-0.125f, -0.583f, 0.188f, -1.083f)
                quadToRelative(0.312f, -0.5f, 0.895f, -0.583f)
                quadToRelative(0.542f, -0.084f, 0.959f, 0.291f)
                quadToRelative(0.416f, 0.375f, 0.541f, 0.917f)
                quadToRelative(0.792f, 4.333f, 4.042f, 7.167f)
                quadToRelative(3.25f, 2.833f, 7.75f, 2.833f)
                quadToRelative(5.083f, 0f, 8.646f, -3.583f)
                quadToRelative(3.562f, -3.584f, 3.562f, -8.667f)
                quadToRelative(0f, -5.042f, -3.583f, -8.521f)
                quadToRelative(-3.583f, -3.479f, -8.667f, -3.479f)
                quadToRelative(-2.791f, 0f, -5.229f, 1.292f)
                quadToRelative(-2.437f, 1.291f, -4.187f, 3.416f)
                horizontalLineToRelative(3.041f)
                quadToRelative(0.542f, 0f, 0.917f, 0.375f)
                reflectiveQuadToRelative(0.375f, 0.917f)
                quadToRelative(0f, 0.583f, -0.375f, 0.958f)
                reflectiveQuadToRelative(-0.917f, 0.375f)
                horizontalLineTo(7.25f)
                quadToRelative(-0.583f, 0f, -0.938f, -0.375f)
                quadToRelative(-0.354f, -0.375f, -0.354f, -0.916f)
                verticalLineTo(7.708f)
                quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                reflectiveQuadToRelative(0.917f, -0.375f)
                quadToRelative(0.583f, 0f, 0.958f, 0.375f)
                reflectiveQuadToRelative(0.375f, 0.916f)
                verticalLineToRelative(2.917f)
                quadToRelative(2.125f, -2.5f, 5.042f, -3.937f)
                quadToRelative(2.917f, -1.438f, 6.208f, -1.438f)
                quadToRelative(3.084f, 0f, 5.792f, 1.146f)
                quadToRelative(2.708f, 1.146f, 4.729f, 3.146f)
                reflectiveQuadToRelative(3.188f, 4.666f)
                quadToRelative(1.166f, 2.667f, 1.166f, 5.75f)
                quadToRelative(0f, 3.042f, -1.166f, 5.75f)
                quadToRelative(-1.167f, 2.709f, -3.188f, 4.709f)
                quadToRelative(-2.021f, 2f, -4.708f, 3.166f)
                quadToRelative(-2.688f, 1.167f, -5.771f, 1.167f)
                close()
            }
        }.build()
        return history!!
    }

private var history: ImageVector? = null
