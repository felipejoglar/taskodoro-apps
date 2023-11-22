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

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.tasks.ui.TaskForm
import com.taskodoro.android.app.ui.components.AppTemplate
import com.taskodoro.android.app.ui.components.appbar.TwoRowsTopAppBar
import com.taskodoro.android.app.ui.components.appbar.model.ActionsList
import com.taskodoro.android.app.ui.components.appbar.model.TopAppBarAction
import com.taskodoro.android.app.ui.components.buttons.TextButton
import com.taskodoro.android.app.ui.components.icons.ArrowBack
import com.taskodoro.android.app.ui.components.icons.Icons
import com.taskodoro.android.app.ui.components.icons.Send
import com.taskodoro.android.app.ui.components.preview.ScreenPreviews
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(
    state: TaskCreateUIState,
    openConfirmationDialog: Boolean,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDueDateChanged: (Long) -> Unit,
    onSubmitClicked: () -> Unit,
    onTaskCreated: () -> Unit,
    onErrorShown: () -> Unit,
    onDismissConfirmationDialog: () -> Unit,
    onDiscardChanges: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.isTaskCreated) {
        if (state.isTaskCreated) onTaskCreated()
    }

    state.error?.let {
        val errorMessage = stringResource(id = it)
        scope.launch {
            onErrorShown()
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 48.dp),
            )
        },
        topBar = {
            TwoRowsTopAppBar(
                title = stringResource(id = R.string.create_new_task_screen_title),
                subtitle = stringResource(id = R.string.project_default_title),
                navigationIcon = navigationIcon(onBackClicked),
                actions = ActionsList(
                    listOf(
                        submitIcon(state.loading, state.submitEnabled, onSubmitClicked),
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

    if (openConfirmationDialog) {
        ConfirmationDialog(
            onDismissConfirmationDialog = onDismissConfirmationDialog,
            onDiscardChanges = onDiscardChanges,
        )
    }
}

@Composable
private fun navigationIcon(
    action: () -> Unit,
) = TopAppBarAction.Icon(
    icon = Icons.ArrowBack,
    contentDescription = stringResource(id = R.string.navigation_back),
    action = action,
)

@Composable
private fun submitIcon(
    isLoading: Boolean,
    enabled: Boolean,
    action: () -> Unit,
) = TopAppBarAction.Icon(
    icon = Icons.Send,
    contentDescription = stringResource(id = R.string.create_new_task_create_task_button),
    tint = MaterialTheme.colorScheme.primary,
    isLoading = isLoading,
    enabled = enabled,
    action = action,
)

@Composable
private fun ConfirmationDialog(
    onDismissConfirmationDialog: () -> Unit,
    onDiscardChanges: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismissConfirmationDialog,
        title = {
            Text(text = stringResource(id = R.string.create_new_task_confirmation_dialog_title))
        },
        text = {
            Text(text = stringResource(id = R.string.create_new_task_confirmation_dialog_text))
        },
        confirmButton = {
            TextButton(onClick = onDiscardChanges) {
                Text(
                    text = stringResource(id = R.string.create_new_task_confirmation_dialog_accept),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissConfirmationDialog) {
                Text(
                    text = stringResource(
                        id = R.string.create_new_task_confirmation_dialog_dismiss,
                    ),
                )
            }
        },
        modifier = modifier,
    )
}

@ScreenPreviews
@Composable
private fun TaskCreateScreenPreview() {
    AppTemplate {
        TaskCreateScreen(
            state = TaskCreateUIState(),
            openConfirmationDialog = false,
            onTitleChanged = {},
            onDescriptionChanged = {},
            onDueDateChanged = {},
            onSubmitClicked = {},
            onTaskCreated = {},
            onErrorShown = {},
            onDismissConfirmationDialog = {},
            onDiscardChanges = {},
            onBackClicked = {},
        )
    }
}

@ScreenPreviews
@Composable
private fun TaskCreateScreenWithDialogPreview() {
    AppTemplate {
        TaskCreateScreen(
            state = TaskCreateUIState(),
            openConfirmationDialog = true,
            onTitleChanged = {},
            onDescriptionChanged = {},
            onDueDateChanged = {},
            onSubmitClicked = {},
            onTaskCreated = {},
            onErrorShown = {},
            onDismissConfirmationDialog = {},
            onDiscardChanges = {},
            onBackClicked = {},
        )
    }
}
