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

import com.taskodoro.model.Uuid
import com.taskodoro.storage.db.LocalTask
import com.taskodoro.tasks.feature.model.Task
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun Task.toLocal() = LocalTask(
    id = id.uuidString,
    title = title,
    description = description,
    completed = isCompleted,
    dueDate = dueDate.toLocal(),
    createdAt = createdAt.toLocal(),
    updatedAt = updatedAt.toLocal(),
)

fun List<LocalTask>.toModels() =
    mapNotNull { task ->
        val id = Uuid.from(task.id)
        if (id != null) id to task else null
    }.map { (id, task) ->
        Task(
            id = id,
            title = task.title,
            description = task.description,
            isCompleted = task.completed,
            dueDate = task.dueDate.toModel(),
            createdAt = task.createdAt.toModel(),
            updatedAt = task.updatedAt.toModel(),
        )
    }

private fun LocalDateTime.toLocal() =
    toInstant(TimeZone.currentSystemDefault()).toString()

private fun String.toModel() =
    Instant.parse(this).toLocalDateTime(TimeZone.currentSystemDefault())
