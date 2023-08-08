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

package com.taskodoro.android.app.tasks.create

import android.content.res.Configuration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.taskodoro.android.R
import com.taskodoro.android.app.tasks.ui.TaskForm
import com.taskodoro.android.app.ui.components.TaskodoroTemplate
import com.taskodoro.android.app.ui.components.appbars.ActionsList
import com.taskodoro.android.app.ui.components.appbars.TaskodoroTopAppBar
import com.taskodoro.android.app.ui.components.appbars.TopAppBarIcon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    state: CreateTaskUIState,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDueDateChanged: (Long) -> Unit,
    onCreateTaskClicked: () -> Unit,
    onTaskCreated: () -> Unit,
    onErrorShown: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.isTaskCreated) {
        if (state.isTaskCreated) onTaskCreated()
    }

    state.error?.let {
        val errorMessage = stringResource(id = state.error)
        scope.launch {
            snackbarHostState.showSnackbar(errorMessage)
            onErrorShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TaskodoroTopAppBar(
                title = stringResource(id = R.string.create_new_task_screen_title),
                subtitle = stringResource(id = R.string.project_default_title),
                navigationIcon = navigationIcon(onBackClicked),
                actions = ActionsList(
                    listOf(
                        submitIcon(state.loading, onCreateTaskClicked),
                    ),
                ),
            )
        },
        modifier = modifier
            .fillMaxSize()
            .consumeWindowInsets(WindowInsets.navigationBars)
            .imePadding(),
    ) { paddingValues ->
        TaskForm(
            title = state.title,
            onTitleChanged = onTitleChanged,
            description = state.description,
            onDescriptionChanged = onDescriptionChanged,
            onDueDateChanged = onDueDateChanged,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        )
    }
}

@Composable
private fun navigationIcon(
    action: () -> Unit,
) = TopAppBarIcon(
    icon = Icons.Rounded.ArrowBack,
    contentDescription = stringResource(id = R.string.navigation_back),
    action = action,
)

@Composable
private fun submitIcon(
    isLoading: Boolean,
    action: () -> Unit,
) = TopAppBarIcon(
    icon = Icons.Rounded.Send,
    contentDescription = stringResource(id = R.string.create_new_task_create_task_button),
    tint = MaterialTheme.colorScheme.primary,
    isLoading = isLoading,
    action = action,
)

@Preview(
    name = "Day Mode",
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Night Mode",
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun CreateTaskScreenPreview() {
    TaskodoroTemplate {
        CreateTaskScreen(
            state = CreateTaskUIState(),
            onTitleChanged = {},
            onDescriptionChanged = {},
            onDueDateChanged = {},
            onCreateTaskClicked = {},
            onTaskCreated = {},
            onErrorShown = {},
            onBackClicked = {},
        )
    }
}
