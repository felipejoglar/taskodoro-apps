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

package com.taskodoro.android.app.ui.components.icons

import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons as MaterialIcons

object Icons

val Icons.ArrowBack: ImageVector
    get() = MaterialIcons.AutoMirrored.Rounded.ArrowBack

val Icons.History: ImageVector
    get() = MaterialIcons.Rounded.History

val Icons.CheckCircle: ImageVector
    get() = MaterialIcons.Rounded.CheckCircle

val Icons.Psychology: ImageVector
    get() = MaterialIcons.Rounded.Psychology

val Icons.Repeat: ImageVector
    get() = MaterialIcons.Rounded.Repeat

val Icons.Add: ImageVector
    get() = MaterialIcons.Rounded.AddCircleOutline
