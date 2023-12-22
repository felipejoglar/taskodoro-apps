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

package com.taskodoro.android.app.tasks.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.AppTemplate
import com.taskodoro.android.app.ui.components.appbar.SingleRowTopAppBar
import com.taskodoro.android.app.ui.components.appbar.model.ActionsList
import com.taskodoro.android.app.ui.components.appbar.model.TopAppBarAction
import com.taskodoro.android.app.ui.components.icons.Add
import com.taskodoro.android.app.ui.components.icons.Icons
import com.taskodoro.android.app.ui.components.preview.ScreenPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onNewTaskClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SingleRowTopAppBar(
                title = stringResource(id = R.string.task_list_title),
                actions = ActionsList(
                    listOf(newIcon(onNewTaskClicked)),
                ),
            )
        },
        modifier = modifier
            .fillMaxSize(),
    ) { paddingValues ->

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Text(text = stringResource(id = R.string.task_list_empty_state))
        }
    }
}

@Composable
private fun newIcon(
    action: () -> Unit,
) = TopAppBarAction.Icon(
    icon = Icons.Add,
    contentDescription = stringResource(id = R.string.task_list_new_task_button),
    tint = MaterialTheme.colorScheme.primary,
    action = action,
)

@ScreenPreviews
@Composable
private fun TaskListScreenPreview() {
    AppTemplate {
        TaskListScreen(onNewTaskClicked = {})
    }
}
