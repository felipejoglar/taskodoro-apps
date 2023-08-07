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

package com.taskodoro.android.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.taskodoro.android.app.tasks.create.CreateTaskScreen
import com.taskodoro.android.app.tasks.create.CreateTaskViewModel
import com.taskodoro.android.app.ui.components.TaskodoroTemplate
import com.taskodoro.storage.db.DatabaseFactory
import com.taskodoro.storage.tasks.LocalTaskRepository
import com.taskodoro.storage.tasks.store.SQLDelightTaskStore
import com.taskodoro.tasks.create.CreateTask
import com.taskodoro.tasks.validator.TaskValidatorFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.time.Instant
import java.time.ZoneId

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val database = DatabaseFactory(applicationContext).create().apply {
            taskdoroDBQueries.clearDB()
        }
        val store = SQLDelightTaskStore(database)
        val repository = LocalTaskRepository(store)
        val validator = TaskValidatorFactory.create()
        val now = { Instant.now().atZone(ZoneId.of("UTC")).toEpochSecond() }
        val createTask = CreateTask(repository, validator, now)

        setContent {
            val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
            val viewModel = CreateTaskViewModel(
                createTask = createTask,
                dispatcher = Dispatchers.IO,
            )

            val state by viewModel.state.collectAsState()

            TaskodoroTemplate {
                CreateTaskScreen(
                    state = state,
                    onTitleChanged = viewModel::onTitleChanged,
                    onDescriptionChanged = viewModel::onDescriptionChanged,
                    onPriorityChanged = viewModel::onPriorityChanged,
                    onDueDateChanged = viewModel::onDueDateChanged,
                    onCreateTaskClicked = viewModel::onCreateTaskClicked,
                    onTaskCreated = {
                        Toast.makeText(this, "Task created!!", Toast.LENGTH_SHORT).show()
                    },
                    onBackClicked = ::finish,
                )
            }

            DisposableEffect(Unit) { onDispose { scope.cancel() } }
        }
    }
}
