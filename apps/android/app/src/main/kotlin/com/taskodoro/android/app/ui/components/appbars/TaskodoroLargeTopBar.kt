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

package com.taskodoro.android.app.ui.components.appbars

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.taskodoro.android.app.ui.theme.TaskodoroTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TaskodoroLargeTopBar(
    title: @Composable () -> Unit,
    onNavigationClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        title = title,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun TaskodoroLargeTopBarPreview() {
    TaskodoroTheme {
        TaskodoroLargeTopBar(
            title = { Text("Taskodoro LargeAppBar") },
            onNavigationClick = { },
        )
    }
}
