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

package com.taskodoro.tasks.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.taskodoro.model.Uuid
import com.taskodoro.storage.db.LocalTask
import com.taskodoro.storage.db.TaskodoroDB
import com.taskodoro.tasks.data.TaskStore
import com.taskodoro.tasks.feature.model.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class SQLDelightTaskStore(
    database: TaskodoroDB,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskStore {

    internal val tasksQueries = database.localTaskQueries

    override suspend fun save(task: Task) {
        withContext(Dispatchers.IO) {
            tasksQueries.insert(
                id = task.id.uuidString,
                title = task.title,
                description = task.description,
                priority = task.priority.ordinal.toLong(),
                completed = false,
                dueDate = task.dueDate,
                createdAt = task.createdAt,
                updatedAt = 0,
            )
        }
    }

    override fun load(): Flow<List<Task>> {
        return tasksQueries.load()
            .asFlow()
            .mapToList(dispatcher)
            .map { it.toModels() }
    }
}

private fun List<LocalTask>.toModels() =
    mapNotNull { task ->
        val id = Uuid.from(task.id)
        if (id != null) id to task else null
    }.map { (id, task) ->
        Task(
            id = id,
            title = task.title,
            description = task.description,
            priority = Task.Priority.fromValue(task.priority?.toInt() ?: 0),
            dueDate = task.dueDate,
            isCompleted = task.completed,
            createdAt = task.createdAt,
            updatedAt = task.updatedAt ?: 0,
        )
    }
