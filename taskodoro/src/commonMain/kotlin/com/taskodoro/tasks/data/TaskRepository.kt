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

package com.taskodoro.tasks.data

import com.taskodoro.tasks.feature.TaskLoader
import com.taskodoro.tasks.feature.TaskSaver
import com.taskodoro.tasks.feature.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val store: TaskStore,
) : TaskSaver, TaskLoader {

    object SaveFailed : Exception()

    override suspend fun save(task: Task): Result<Unit> =
        try {
            store.save(task)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(SaveFailed)
        }

    override fun load(): Flow<List<Task>> {
        return store.load()
    }
}
