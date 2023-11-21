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

val Icons.Psychology: ImageVector
    get() {
        if (psychology != null) {
            return psychology!!
        }
        psychology = ImageVector.Builder(
            name = "Psychology",
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
                moveTo(16.75f, 34.708f)
                quadToRelative(-0.583f, 0f, -0.938f, -0.354f)
                quadToRelative(-0.354f, -0.354f, -0.354f, -0.937f)
                verticalLineToRelative(-3.834f)
                horizontalLineToRelative(-2.666f)
                quadToRelative(-1.042f, 0f, -1.813f, -0.791f)
                quadToRelative(-0.771f, -0.792f, -0.771f, -1.834f)
                verticalLineToRelative(-4.291f)
                horizontalLineTo(8.542f)
                quadToRelative(-0.834f, 0f, -1.271f, -0.771f)
                quadToRelative(-0.438f, -0.771f, 0.021f, -1.604f)
                lineToRelative(2.958f, -5.25f)
                quadToRelative(0.833f, -4.375f, 4f, -7.084f)
                quadToRelative(3.167f, -2.708f, 7.417f, -2.708f)
                quadToRelative(4.708f, 0f, 8.083f, 3.354f)
                reflectiveQuadToRelative(3.375f, 8.063f)
                quadToRelative(0f, 3.458f, -1.917f, 6.166f)
                quadToRelative(-1.916f, 2.709f, -5.083f, 4.25f)
                verticalLineToRelative(6.334f)
                quadToRelative(0f, 0.583f, -0.375f, 0.937f)
                quadToRelative(-0.375f, 0.354f, -0.958f, 0.354f)
                close()
                moveToRelative(1.333f, -2.625f)
                horizontalLineToRelative(5.375f)
                verticalLineToRelative(-6.708f)
                lineToRelative(1.25f, -0.583f)
                quadToRelative(2.625f, -1.167f, 4.188f, -3.334f)
                quadToRelative(1.562f, -2.166f, 1.562f, -4.791f)
                quadToRelative(0f, -3.625f, -2.562f, -6.209f)
                quadToRelative(-2.563f, -2.583f, -6.188f, -2.583f)
                quadToRelative(-3.583f, 0f, -6.083f, 2.354f)
                reflectiveQuadToRelative(-2.75f, 5.521f)
                lineTo(10.5f, 20.042f)
                horizontalLineToRelative(2.333f)
                verticalLineToRelative(6.916f)
                horizontalLineToRelative(5.25f)
                close()
                moveToRelative(2.875f, -9.291f)
                horizontalLineToRelative(1.417f)
                quadToRelative(0.292f, 0f, 0.458f, -0.167f)
                quadToRelative(0.167f, -0.167f, 0.209f, -0.417f)
                lineToRelative(0.125f, -1.25f)
                quadToRelative(0.5f, -0.125f, 0.875f, -0.375f)
                reflectiveQuadToRelative(0.666f, -0.5f)
                lineToRelative(1.167f, 0.417f)
                quadToRelative(0.25f, 0.083f, 0.479f, 0f)
                quadToRelative(0.229f, -0.083f, 0.354f, -0.292f)
                lineToRelative(0.667f, -1.166f)
                quadToRelative(0.083f, -0.25f, 0.083f, -0.48f)
                quadToRelative(0f, -0.229f, -0.208f, -0.395f)
                lineToRelative(-0.875f, -0.667f)
                quadToRelative(0.167f, -0.5f, 0.167f, -1.062f)
                quadToRelative(0f, -0.563f, -0.167f, -1.063f)
                lineToRelative(0.875f, -0.667f)
                quadToRelative(0.208f, -0.166f, 0.208f, -0.395f)
                quadToRelative(0f, -0.23f, -0.083f, -0.48f)
                lineToRelative(-0.667f, -1.166f)
                quadToRelative(-0.125f, -0.209f, -0.354f, -0.292f)
                quadToRelative(-0.229f, -0.083f, -0.479f, 0f)
                lineToRelative(-1.167f, 0.417f)
                quadToRelative(-0.291f, -0.25f, -0.687f, -0.479f)
                quadToRelative(-0.396f, -0.23f, -0.854f, -0.355f)
                lineToRelative(-0.125f, -1.25f)
                quadToRelative(-0.042f, -0.291f, -0.209f, -0.458f)
                quadToRelative(-0.166f, -0.167f, -0.458f, -0.167f)
                horizontalLineToRelative(-1.417f)
                quadToRelative(-0.291f, 0f, -0.458f, 0.167f)
                quadToRelative(-0.167f, 0.167f, -0.208f, 0.458f)
                lineToRelative(-0.125f, 1.25f)
                quadToRelative(-0.459f, 0.125f, -0.855f, 0.355f)
                quadToRelative(-0.395f, 0.229f, -0.687f, 0.479f)
                lineToRelative(-1.167f, -0.417f)
                quadToRelative(-0.25f, -0.083f, -0.479f, 0f)
                quadToRelative(-0.229f, 0.083f, -0.312f, 0.292f)
                lineToRelative(-0.709f, 1.166f)
                quadToRelative(-0.083f, 0.25f, -0.083f, 0.48f)
                quadToRelative(0f, 0.229f, 0.25f, 0.395f)
                lineToRelative(0.833f, 0.667f)
                quadToRelative(-0.166f, 0.5f, -0.166f, 1.063f)
                quadToRelative(0f, 0.562f, 0.166f, 1.062f)
                lineToRelative(-0.833f, 0.667f)
                quadToRelative(-0.25f, 0.166f, -0.25f, 0.395f)
                quadToRelative(0f, 0.23f, 0.083f, 0.48f)
                lineToRelative(0.709f, 1.166f)
                quadToRelative(0.083f, 0.209f, 0.312f, 0.292f)
                quadToRelative(0.229f, 0.083f, 0.479f, 0f)
                lineToRelative(1.167f, -0.417f)
                quadToRelative(0.292f, 0.25f, 0.667f, 0.5f)
                quadToRelative(0.375f, 0.25f, 0.875f, 0.375f)
                lineToRelative(0.125f, 1.209f)
                quadToRelative(0.041f, 0.291f, 0.208f, 0.458f)
                quadToRelative(0.167f, 0.167f, 0.458f, 0.167f)
                close()
                moveToRelative(0.709f, -3.584f)
                quadToRelative(-1.167f, 0f, -1.979f, -0.791f)
                quadToRelative(-0.813f, -0.792f, -0.813f, -1.959f)
                quadToRelative(0f, -1.166f, 0.813f, -1.979f)
                quadToRelative(0.812f, -0.812f, 1.979f, -0.812f)
                quadToRelative(1.166f, 0f, 1.979f, 0.812f)
                quadToRelative(0.812f, 0.813f, 0.812f, 1.979f)
                quadToRelative(0f, 1.167f, -0.812f, 1.959f)
                quadToRelative(-0.813f, 0.791f, -1.979f, 0.791f)
                close()
                moveToRelative(-1.167f, 0.75f)
                close()
            }
        }.build()
        return psychology!!
    }

private var psychology: ImageVector? = null
