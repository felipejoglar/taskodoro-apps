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

package com.taskodoro.android.app.navigation.graphs.tasks

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.taskodoro.android.app.tasks.list.TaskListScreen
import com.taskodoro.android.app.tasks.list.TaskListViewModel
import com.taskodoro.tasks.feature.TaskLoader
import com.taskodoro.tasks.feature.model.Task
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.viewmodel.viewModel

const val TASK_LIST_ROUTE = "$TASK_GRAPH_ROUTE/list"

fun Navigator.navigateToTaskList(options: NavOptions? = null) {
    navigate(TASK_LIST_ROUTE, options)
}

fun RouteBuilder.taskListScreen(
    taskLoader: TaskLoader,
    onTaskClicked: (Task) -> Unit,
    onNewTaskClicked: () -> Unit,
    onKnowMoreClicked: () -> Unit,
) {
    scene(
        route = TASK_LIST_ROUTE,
    ) {
        val viewModel = viewModel {
            TaskListViewModel(taskLoader = taskLoader)
        }

        val state by viewModel.uiState.collectAsState()

        TaskListScreen(
            state = state,
            onTaskClicked = onTaskClicked,
            onNewTaskClicked = onNewTaskClicked,
            onKnowMoreClicked = onKnowMoreClicked,
        )
    }
}
