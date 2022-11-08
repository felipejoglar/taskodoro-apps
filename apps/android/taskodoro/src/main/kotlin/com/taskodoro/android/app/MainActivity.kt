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

package com.taskodoro.android.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.taskodoro.android.app.tasks.create.CreateTaskScreen
import com.taskodoro.android.app.tasks.create.CreateTaskViewModel
import com.taskodoro.android.app.ui.components.TaskodoroTemplate
import com.taskodoro.storage.db.DriverFactory
import com.taskodoro.storage.db.TaskodoroDB
import com.taskodoro.storage.tasks.LocalTaskRepository
import com.taskodoro.storage.tasks.store.SQLDelightTaskStore
import com.taskodoro.tasks.TaskValidator
import com.taskodoro.tasks.save
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val sqlDriver = DriverFactory(applicationContext).createDriver()
        val database = TaskodoroDB(sqlDriver).apply { taskdoroDBQueries.clearDB() }
        val store = SQLDelightTaskStore(database)
        val repository = LocalTaskRepository(store)

        setContent {
            val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
            val viewModel = CreateTaskViewModel(
                createTask = { task ->
                    flowOf(save(task, repository, TaskValidator::validate))
                        .flowOn(Dispatchers.Default)
                },
                scope = scope
            )

            val state by viewModel.state.collectAsState()

            TaskodoroTemplate {
                CreateTaskScreen(
                    state = state,
                    onTitleChanged = viewModel::onTitleChanged,
                    onDescriptionChanged = viewModel::onDescriptionChanged,
                    onPriorityChanged = viewModel::onPriorityChanged,
                    onCreateTaskClicked = viewModel::create,
                    onTaskCreated = {
                        Toast.makeText(this, "Task created!!", Toast.LENGTH_SHORT).show()
                    },
                    onBackClicked = ::finish
                )
            }
        }
    }
}