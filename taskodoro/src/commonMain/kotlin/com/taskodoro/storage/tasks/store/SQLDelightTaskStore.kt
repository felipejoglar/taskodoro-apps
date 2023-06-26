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

package com.taskodoro.storage.tasks.store

import com.taskodoro.storage.db.TaskodoroDB
import com.taskodoro.storage.tasks.TaskStore
import com.taskodoro.tasks.model.Task

class SQLDelightTaskStore(
    database: TaskodoroDB
) : TaskStore {

    internal val tasksQueries = database.localTaskQueries

    override fun save(task: Task) {
        tasksQueries.insert(
            id = task.id,
            title = task.title,
            description = task.description,
            priority = task.priority.ordinal.toLong(),
            completed = false,
            createdAt = task.createdAt,
            updatedAt = 0,
        )
    }
}