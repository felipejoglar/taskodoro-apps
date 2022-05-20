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

package com.taskodoro.android.app.tasks

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taskodoro.android.app.di.TasksUIComposer
import com.taskodoro.android.app.ui.theme.TaskodoroTheme
import com.taskodoro.tasks.model.Task

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = TasksUIComposer.tasksViewModel()
) {
    val tasks = viewModel.tasks.collectAsState()

    LaunchedEffect(tasks) {
        viewModel.getTasks()
    }

    TasksContent(tasks.value)
}

@Composable
fun TasksContent(tasks: List<Task>) {
    LazyColumn {
        items(tasks) { task ->
            TaskItem(task)
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Text(
        text = task.description,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
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
private fun TasksScreenPreview() {
    TaskodoroTheme {
        androidx.compose.material.Surface(
            color = MaterialTheme.colors.background
        ) {
            val tasks = List(20) {
                Task(id = it.toString(), description = "Task $it description")
            }
            TasksContent(tasks = tasks)
        }

    }
}