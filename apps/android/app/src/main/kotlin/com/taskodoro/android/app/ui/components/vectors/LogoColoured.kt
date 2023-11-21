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

package com.taskodoro.android.app.ui.components.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var logoColoured: ImageVector? = null

public val Vectors.LogoColoured: ImageVector
    get() {
        if (logoColoured != null) {
            return logoColoured!!
        }
        logoColoured = ImageVector.Builder(
            name = "Logo Coloured",
            defaultWidth = 256.dp,
            defaultHeight = 256.dp,
            viewportWidth = 256f,
            viewportHeight = 256f,
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFFBE81F)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.EvenOdd,
                ) {
                    moveTo(233.586f, 41.3721f)
                    curveTo(239.456f, 47.2181f, 239.475f, 56.7151f, 233.629f, 62.5851f)
                    lineTo(139.014f, 157.585f)
                    curveTo(137.62f, 158.984f, 135.965f, 160.094f, 134.142f, 160.852f)
                    curveTo(132.318f, 161.61f, 130.363f, 162f, 128.389f, 162f)
                    curveTo(126.415f, 162.001f, 124.46f, 161.611f, 122.636f, 160.855f)
                    curveTo(120.813f, 160.098f, 119.156f, 158.988f, 117.763f, 157.59f)
                    lineTo(89.3776f, 129.119f)
                    curveTo(83.5286f, 123.252f, 83.5436f, 113.755f, 89.4106f, 107.906f)
                    curveTo(95.2766f, 102.057f, 104.775f, 102.071f, 110.624f, 107.938f)
                    lineTo(128.38f, 125.749f)
                    lineTo(212.373f, 41.4151f)
                    curveTo(218.219f, 35.5451f, 227.716f, 35.5261f, 233.586f, 41.3721f)
                    close()
                    moveTo(110.37f, 49.9673f)
                    curveTo(127.197f, 46.1654f, 144.803f, 47.9048f, 160.561f, 54.9261f)
                    curveTo(168.128f, 58.2971f, 176.996f, 54.8971f, 180.367f, 47.3291f)
                    curveTo(183.739f, 39.7621f, 180.338f, 30.8951f, 172.771f, 27.5231f)
                    curveTo(156.582f, 20.3096f, 138.889f, 17.1148f, 121.2f, 18.2104f)
                    curveTo(103.511f, 19.3061f, 86.3479f, 24.6598f, 71.1729f, 33.8159f)
                    curveTo(55.9979f, 42.9719f, 43.2599f, 55.6592f, 34.0434f, 70.7976f)
                    curveTo(24.8269f, 85.936f, 19.4048f, 103.077f, 18.2386f, 120.762f)
                    curveTo(16.6774f, 144.431f, 22.8094f, 167.971f, 35.7202f, 187.871f)
                    curveTo(48.6309f, 207.77f, 67.6286f, 222.963f, 89.8796f, 231.183f)
                    curveTo(106.505f, 237.325f, 124.367f, 239.358f, 141.947f, 237.111f)
                    curveTo(159.527f, 234.864f, 176.304f, 228.402f, 190.849f, 218.275f)
                    curveTo(205.395f, 208.149f, 217.278f, 194.658f, 225.488f, 178.951f)
                    curveTo(233.697f, 163.244f, 237.99f, 145.786f, 238.001f, 128.063f)
                    verticalLineTo(119.314f)
                    curveTo(238.001f, 111.03f, 231.285f, 104.314f, 223.001f, 104.314f)
                    curveTo(214.717f, 104.314f, 208.001f, 111.03f, 208.001f, 119.314f)
                    verticalLineTo(128.046f)
                    curveTo(207.991f, 145.297f, 202.405f, 162.084f, 192.075f, 175.901f)
                    curveTo(181.746f, 189.718f, 167.227f, 199.826f, 150.683f, 204.717f)
                    curveTo(134.14f, 209.609f, 116.459f, 209.021f, 100.276f, 203.043f)
                    curveTo(84.094f, 197.064f, 70.2778f, 186.015f, 60.8882f, 171.543f)
                    curveTo(51.4987f, 157.07f, 47.0389f, 139.951f, 48.174f, 122.737f)
                    curveTo(49.309f, 105.523f, 55.9782f, 89.1367f, 67.1868f, 76.0227f)
                    curveTo(78.3954f, 62.9086f, 93.5428f, 53.7691f, 110.37f, 49.9673f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFFACDC47)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.EvenOdd,
                ) {
                    moveTo(192.915f, 60.952f)
                    lineTo(128.38f, 125.749f)
                    lineTo(110.624f, 107.938f)
                    curveTo(104.775f, 102.071f, 95.2766f, 102.057f, 89.4106f, 107.906f)
                    curveTo(83.5436f, 113.755f, 83.5286f, 123.252f, 89.3776f, 129.119f)
                    lineTo(117.763f, 157.59f)
                    curveTo(119.156f, 158.988f, 120.813f, 160.098f, 122.636f, 160.855f)
                    curveTo(124.46f, 161.611f, 126.415f, 162.001f, 128.389f, 162f)
                    curveTo(130.363f, 162f, 132.318f, 161.61f, 134.142f, 160.852f)
                    curveTo(135.965f, 160.094f, 137.62f, 158.984f, 139.014f, 157.585f)
                    lineTo(230.532f, 65.6941f)
                    curveTo(219.86f, 63.3914f, 209.187f, 62.4279f, 198.488f, 61.4622f)
                    curveTo(196.631f, 61.2946f, 194.773f, 61.1268f, 192.915f, 60.952f)
                    close()
                    moveTo(138.492f, 48.6913f)
                    curveTo(129.175f, 47.4588f, 119.654f, 47.8696f, 110.37f, 49.9672f)
                    curveTo(93.5428f, 53.7691f, 78.3954f, 62.9086f, 67.1868f, 76.0227f)
                    curveTo(55.9782f, 89.1367f, 49.309f, 105.523f, 48.174f, 122.737f)
                    curveTo(47.0389f, 139.951f, 51.4987f, 157.07f, 60.8882f, 171.543f)
                    curveTo(70.2778f, 186.015f, 84.094f, 197.064f, 100.276f, 203.043f)
                    curveTo(116.459f, 209.021f, 134.14f, 209.609f, 150.683f, 204.717f)
                    curveTo(167.227f, 199.826f, 181.746f, 189.718f, 192.075f, 175.901f)
                    curveTo(202.405f, 162.083f, 207.991f, 145.297f, 208.001f, 128.046f)
                    verticalLineTo(119.314f)
                    curveTo(208.001f, 111.03f, 214.717f, 104.314f, 223.001f, 104.314f)
                    curveTo(231.285f, 104.314f, 238.001f, 111.03f, 238.001f, 119.314f)
                    verticalLineTo(128.063f)
                    curveTo(237.99f, 145.786f, 233.697f, 163.244f, 225.488f, 178.951f)
                    curveTo(217.278f, 194.658f, 205.395f, 208.149f, 190.849f, 218.275f)
                    curveTo(176.304f, 228.402f, 159.527f, 234.864f, 141.947f, 237.111f)
                    curveTo(124.367f, 239.358f, 106.505f, 237.325f, 89.8796f, 231.183f)
                    curveTo(67.6286f, 222.963f, 48.6309f, 207.77f, 35.7202f, 187.871f)
                    curveTo(22.8094f, 167.971f, 16.6774f, 144.431f, 18.2386f, 120.762f)
                    curveTo(19.4048f, 103.077f, 24.8269f, 85.9359f, 34.0434f, 70.7976f)
                    curveTo(43.2599f, 55.6592f, 55.9979f, 42.9719f, 71.1729f, 33.8158f)
                    curveTo(72.2205f, 33.1837f, 73.2776f, 32.5698f, 74.3437f, 31.9741f)
                    curveTo(80.6941f, 32.1971f, 87.0579f, 32.8101f, 93.44f, 34.04f)
                    curveTo(105.826f, 36.3746f, 118.317f, 41.0922f, 130.8f, 45.8066f)
                    curveTo(133.365f, 46.7752f, 135.929f, 47.7436f, 138.492f, 48.6913f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF68C869)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.EvenOdd,
                ) {
                    moveTo(180.661f, 115.769f)
                    curveTo(174.641f, 115.33f, 168.609f, 115.415f, 162.56f, 116.36f)
                    curveTo(146.788f, 118.837f, 130.846f, 127.224f, 114.968f, 135.577f)
                    curveTo(110.751f, 137.795f, 106.538f, 140.012f, 102.334f, 142.114f)
                    lineTo(117.763f, 157.59f)
                    curveTo(119.156f, 158.988f, 120.813f, 160.098f, 122.636f, 160.855f)
                    curveTo(124.46f, 161.611f, 126.415f, 162.001f, 128.389f, 162f)
                    curveTo(130.363f, 162f, 132.318f, 161.61f, 134.142f, 160.852f)
                    curveTo(135.965f, 160.094f, 137.62f, 158.984f, 139.014f, 157.585f)
                    lineTo(180.661f, 115.769f)
                    close()
                    moveTo(238.001f, 125.061f)
                    verticalLineTo(128.063f)
                    curveTo(237.99f, 145.786f, 233.697f, 163.244f, 225.488f, 178.951f)
                    curveTo(217.278f, 194.658f, 205.395f, 208.149f, 190.849f, 218.276f)
                    curveTo(176.304f, 228.402f, 159.527f, 234.864f, 141.947f, 237.111f)
                    curveTo(124.367f, 239.358f, 106.505f, 237.325f, 89.8796f, 231.183f)
                    curveTo(67.6286f, 222.963f, 48.6309f, 207.77f, 35.7202f, 187.871f)
                    curveTo(30.7501f, 180.21f, 26.7845f, 172.01f, 23.8757f, 163.471f)
                    curveTo(24.1838f, 163.448f, 24.4919f, 163.424f, 24.8f, 163.4f)
                    curveTo(34.6363f, 162.624f, 44.4726f, 161.289f, 54.3281f, 159.183f)
                    curveTo(56.1404f, 163.465f, 58.3315f, 167.602f, 60.8882f, 171.543f)
                    curveTo(70.2778f, 186.015f, 84.094f, 197.064f, 100.276f, 203.043f)
                    curveTo(116.459f, 209.021f, 134.14f, 209.609f, 150.683f, 204.717f)
                    curveTo(167.227f, 199.826f, 181.746f, 189.718f, 192.075f, 175.901f)
                    curveTo(202.405f, 162.084f, 207.991f, 145.297f, 208.001f, 128.046f)
                    verticalLineTo(120.207f)
                    curveTo(208.249f, 120.259f, 208.498f, 120.311f, 208.746f, 120.363f)
                    curveTo(216.237f, 121.93f, 223.718f, 123.494f, 231.2f, 124.4f)
                    curveTo(233.467f, 124.674f, 235.734f, 124.891f, 238.001f, 125.061f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF2BAF80)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.EvenOdd,
                ) {
                    moveTo(218.702f, 190.233f)
                    curveTo(211.208f, 201.156f, 201.78f, 210.666f, 190.849f, 218.275f)
                    curveTo(176.304f, 228.402f, 159.527f, 234.864f, 141.947f, 237.111f)
                    curveTo(124.367f, 239.358f, 106.505f, 237.325f, 89.8796f, 231.183f)
                    curveTo(77.9174f, 226.764f, 66.8955f, 220.33f, 57.2542f, 212.232f)
                    curveTo(69.273f, 217.099f, 81.3233f, 221.158f, 93.44f, 221.6f)
                    curveTo(111.472f, 222.261f, 129.726f, 214.678f, 147.853f, 207.147f)
                    curveTo(152.767f, 205.106f, 157.672f, 203.068f, 162.56f, 201.2f)
                    curveTo(181.391f, 194.04f, 200.061f, 189.205f, 218.702f, 190.233f)
                    close()
                }
            }
        }.build()
        return logoColoured!!
    }
