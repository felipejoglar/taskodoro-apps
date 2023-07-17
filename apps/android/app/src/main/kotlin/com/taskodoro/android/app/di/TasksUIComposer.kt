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

package com.taskodoro.android.app.di

import com.taskodoro.android.app.tasks.TasksViewModel
import com.taskodoro.storage.tasks.InMemoryTaskDataSource
import com.taskodoro.tasks.data.TaskRepository
import com.taskodoro.tasks.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object TasksUIComposer {

    private val tasks: Flow<List<Task>> by lazy {
        flowEmitting { repository.getTasks() }
            .flowOn(Dispatchers.IO)
    }

    private val repository: TaskRepository by lazy {
        TaskRepository(localDataSource = InMemoryTaskDataSource())
    }

    fun tasksViewModel(): TasksViewModel = TasksViewModel(getTasks = tasks)
}

inline fun <T> flowEmitting(crossinline block: suspend () -> T): Flow<T> = flow { emit(block()) }
