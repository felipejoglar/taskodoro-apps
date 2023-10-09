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

val Icons.Send: ImageVector
    get() {
        if (send != null) {
            return send!!
        }
        send = ImageVector.Builder(
            name = "send",
            defaultWidth = 28.0.dp,
            defaultHeight = 28.0.dp,
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
                moveTo(5.208f, 31.125f)
                verticalLineTo(8.875f)
                quadToRelative(0f, -0.708f, 0.604f, -1.083f)
                quadToRelative(0.605f, -0.375f, 1.23f, -0.125f)
                lineToRelative(26.333f, 11.125f)
                quadToRelative(0.792f, 0.333f, 0.792f, 1.187f)
                quadToRelative(0f, 0.854f, -0.792f, 1.188f)
                lineTo(7.042f, 32.292f)
                quadToRelative(-0.625f, 0.291f, -1.23f, -0.104f)
                quadToRelative(-0.604f, -0.396f, -0.604f, -1.063f)
                close()
                moveTo(7.875f, 29f)
                lineToRelative(21.5f, -9.042f)
                lineToRelative(-21.5f, -9.083f)
                verticalLineToRelative(6.583f)
                lineToRelative(9.833f, 2.5f)
                lineToRelative(-9.833f, 2.459f)
                close()
                moveToRelative(0f, -9.042f)
                verticalLineToRelative(-9.083f)
                verticalLineTo(29f)
                close()
            }
        }.build()
        return send!!
    }

private var send: ImageVector? = null
