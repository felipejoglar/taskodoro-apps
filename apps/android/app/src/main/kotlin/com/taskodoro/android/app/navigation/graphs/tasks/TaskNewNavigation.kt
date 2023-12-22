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

package com.taskodoro.android.app.navigation.graphs.tasks

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.taskodoro.android.app.tasks.new.TaskNewScreen
import com.taskodoro.android.app.tasks.new.TaskNewViewModel
import com.taskodoro.storage.tasks.LocalTaskRepository
import com.taskodoro.storage.tasks.TaskStoreFactory
import com.taskodoro.tasks.new.TaskNew
import com.taskodoro.tasks.validator.TaskValidatorFactory
import kotlinx.coroutines.Dispatchers
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.viewmodel.viewModel
import java.time.Instant
import java.time.ZoneId

const val TaskNewRoute = "$TaskGraphRoute/new"

fun Navigator.navigateToTaskNew(options: NavOptions? = null) {
    navigate(TaskNewRoute, options)
}

fun RouteBuilder.taskNewScreen(
    context: Context,
    onNewTask: () -> Unit,
    onDiscardChanges: () -> Unit,
) {
    scene(
        route = TaskNewRoute,
    ) {
        val viewModel = viewModel { savedStateHolder ->
            val repository = LocalTaskRepository(TaskStoreFactory(context).create())
            val validator = TaskValidatorFactory.create()

            val taskNew = TaskNew(
                repository = repository,
                validator = validator,
                now = { Instant.now().atZone(ZoneId.of("UTC")).toEpochSecond() }
            )
            TaskNewViewModel(taskNew, Dispatchers.IO, savedStateHolder)
        }

        val state by viewModel.uiState.collectAsState()
        var openConfirmationDialog by remember { mutableStateOf(false) }

        val onBackPressed = {
            if (state.title.isNotBlank()) {
                openConfirmationDialog = true
            } else {
                onDiscardChanges()
            }
        }

        BackHandler(enabled = state.title.isNotEmpty(), onBack = onBackPressed)

        TaskNewScreen(
            state = state,
            openConfirmationDialog = openConfirmationDialog,
            onTitleChanged = viewModel::onTitleChanged,
            onDescriptionChanged = viewModel::onDescriptionChanged,
            onDueDateChanged = viewModel::onDueDateChanged,
            onSubmitClicked = viewModel::onSubmitClicked,
            onNewTask = onNewTask,
            onErrorShown = viewModel::onErrorShown,
            onDismissConfirmationDialog = { openConfirmationDialog = false },
            onDiscardChanges = onDiscardChanges,
            onBackClicked = { onBackPressed() },
        )
    }
}