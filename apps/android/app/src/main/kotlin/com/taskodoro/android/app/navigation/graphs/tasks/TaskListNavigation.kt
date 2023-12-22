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

import com.taskodoro.android.app.tasks.list.TaskListScreen
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

const val TaskListRoute = "$TaskGraphRoute/list"

fun Navigator.navigateToTaskList(options: NavOptions? = null) {
    navigate(TaskListRoute, options)
}

fun RouteBuilder.taskListScreen(
    onNewTaskClicked: () -> Unit,
) {
    scene(
        route = TaskListRoute,
    ) {
        TaskListScreen(onNewTaskClicked)
    }
}