/*
 *    Copyright 2022 Felipe Joglar
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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.tasks.ui.TaskForm
import com.taskodoro.android.app.ui.components.TaskodoroTemplate
import com.taskodoro.android.app.ui.components.appbars.TaskodoroLargeTopBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateTaskScreen(
    state: CreateTaskUIState,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onPriorityChanged: (Int) -> Unit,
    onCreateTaskClicked: () -> Unit,
    onTaskCreated: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isTaskCreated) onTaskCreated()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .consumedWindowInsets(WindowInsets.navigationBars)
            .imePadding(),
        topBar = {
            TaskodoroLargeTopBar(
                title = {
                    Text(
                        stringResource(id = R.string.create_new_task_screen_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onNavigationClick = onBackClicked,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        TaskForm(
            title = state.title,
            onTitleChanged = onTitleChanged,
            description = state.description,
            onDescriptionChanged = onDescriptionChanged,
            priority = state.priority,
            onPriorityChanged = onPriorityChanged,
            submitLabel = R.string.create_new_task_create_task_button,
            onSubmitClicked = { onCreateTaskClicked() },
            loading = state.loading,
            titleErrorLabel = state.titleError,
            errorLabel = state.error,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        )
    }
}

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
            onPriorityChanged = {},
            onCreateTaskClicked = {},
            onTaskCreated = {},
            onBackClicked = {}
        )
    }
}

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
private fun CreateTaskScreenWithErrorsPreview() {
    TaskodoroTemplate {
        CreateTaskScreen(
            state = CreateTaskUIState(
                titleError = R.string.create_new_task_empty_title_error,
                error = R.string.create_new_task_unknown_error
            ),
            onTitleChanged = {},
            onDescriptionChanged = {},
            onPriorityChanged = {},
            onCreateTaskClicked = {},
            onTaskCreated = {},
            onBackClicked = {}
        )
    }
}