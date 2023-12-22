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
import android.widget.Toast
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

const val TASK_GRAPH_ROUTE = "task"

fun Navigator.navigateToTaskGraph(options: NavOptions? = null) {
    navigate(TASK_GRAPH_ROUTE, options)
}

fun RouteBuilder.taskGraph(
    context: Context,
    navigator: Navigator,
    onKnowMoreClicked: () -> Unit,
) {
    group(
        route = TASK_GRAPH_ROUTE,
        initialRoute = TASK_LIST_ROUTE,
    ) {
        taskListScreen(
            onNewTaskClicked = { navigator.navigateToTaskNew() },
            onKnowMoreClicked = onKnowMoreClicked,
        )

        taskNewScreen(
            context = context,
            onNewTask = {
                Toast.makeText(context, "Task saved!", Toast.LENGTH_SHORT).show()
                navigator.goBack()
            },
            onDiscardChanges = { navigator.goBack() },
        )
    }
}
