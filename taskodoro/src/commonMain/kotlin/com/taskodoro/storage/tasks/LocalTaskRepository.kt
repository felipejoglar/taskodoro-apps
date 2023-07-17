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

package com.taskodoro.storage.tasks

import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.model.Task

class LocalTaskRepository(
    private val store: TaskStore,
) : TaskRepository {

    override fun save(task: Task): Result<Unit> =
        try {
            store.save(task)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(TaskRepository.TaskException.SaveFailed)
        }
}
