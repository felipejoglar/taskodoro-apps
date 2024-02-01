/*
 *    Copyright 2024 Felipe Joglar
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

@file:OptIn(ExperimentalFoundationApi::class)

package com.taskodoro.android.app.tasks.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.AppTemplate
import com.taskodoro.android.app.ui.components.appbar.SingleRowTopAppBar
import com.taskodoro.android.app.ui.components.appbar.model.ActionsList
import com.taskodoro.android.app.ui.components.appbar.model.OverflowList
import com.taskodoro.android.app.ui.components.appbar.model.TopAppBarAction
import com.taskodoro.android.app.ui.components.appbar.model.TopAppBarOverflow
import com.taskodoro.android.app.ui.components.icons.Add
import com.taskodoro.android.app.ui.components.icons.Icons
import com.taskodoro.android.app.ui.components.preview.ScreenPreviews
import com.taskodoro.model.Uuid
import com.taskodoro.tasks.feature.model.Task
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    state: TaskListUiState,
    onTaskClicked: (Task) -> Unit,
    onNewTaskClicked: () -> Unit,
    onKnowMoreClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            SingleRowTopAppBar(
                title = stringResource(id = R.string.task_list_title),
                actions = ActionsList(
                    listOf(
                        newIcon(onNewTaskClicked),
                    ),
                ),
                overflow = OverflowList(
                    listOf(
                        knowMoreOverflowMenuItem(onKnowMoreClicked),
                    ),
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
            if (state.tasks.isEmpty()) {
                Text(text = stringResource(id = R.string.task_list_empty_state))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    stickyHeader {
                        Text(
                            text = "Today",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,

                            )
                    }
                    items(state.tasks, { it.id.uuidString }) { task ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(task.title)
                                        onTaskClicked(task)
                                    }
                                }
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            task.description?.let { description ->
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            Text(
                                text = "Due on ${task.dueDate}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = "Created at ${task.createdAt}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = "Updated at ${task.updatedAt}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
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

@Composable
fun knowMoreOverflowMenuItem(
    onKnowMoreClicked: () -> Unit,
) = TopAppBarOverflow(
    text = stringResource(id = R.string.task_list_know_more_menu_item),
    action = onKnowMoreClicked,
)

@ScreenPreviews
@Composable
private fun TaskListScreenEmptyPreview() {
    AppTemplate {
        TaskListScreen(
            state = TaskListUiState(),
            onTaskClicked = {},
            onNewTaskClicked = {},
            onKnowMoreClicked = {},
        )
    }
}

@ScreenPreviews
@Composable
private fun TaskListScreenWithTasksPreview() {
    val tasks = List(PREVIEW_TASK_LIST_SIZE) {
        previewTask(it)
    }
    AppTemplate {
        TaskListScreen(
            state = TaskListUiState(tasks = tasks),
            onTaskClicked = {},
            onNewTaskClicked = {},
            onKnowMoreClicked = {},
        )
    }
}

private const val PREVIEW_TASK_LIST_SIZE = 10
private const val NON_DESCRIPTION_TASK = 3
private fun previewTask(index: Int) : Task {
    return Task(
        Uuid(),
        "Task title $index",
        if (index % NON_DESCRIPTION_TASK == 0) "Task description $index" else null,
        isCompleted = true,
        dueDate = previewDate(2),
        createdAt = previewDate(),
        updatedAt = previewDate(1),
    )
}

private fun previewDate(offset: Int = 0): LocalDateTime {
    return Clock.System.now()
        .plus(offset, DateTimeUnit.HOUR, TimeZone.UTC)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}