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

package com.taskodoro.android.app.ui.components.appbar.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopAppBarAction {

    data class Icon(
        val icon: ImageVector,
        val contentDescription: String,
        val tint: Color? = null,
        val isLoading: Boolean = false,
        val enabled: Boolean = true,
        val action: () -> Unit,
    ) : TopAppBarAction()

    data class Button(
        val text: String,
        val action: () -> Unit,
    ) : TopAppBarAction()
}

@Immutable
data class ActionsList(
    val items: List<TopAppBarAction>,
)
