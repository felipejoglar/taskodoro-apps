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

package com.taskodoro.storage.tasks

import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.TaskRepository.TaskException
import com.taskodoro.tasks.model.Task
import com.taskodoro.tasks.model.TaskValidationResult
import com.taskodoro.tasks.model.TaskValidationResult.EMPTY_TITLE
import com.taskodoro.tasks.model.TaskValidationResult.INVALID_TITLE
import com.taskodoro.tasks.model.TaskValidationResult.SUCCESS

class LocalTaskRepository(
    private val store: TaskStore,
    private val validate: (Task) -> TaskValidationResult,
) : TaskRepository {

    override fun save(task: Task): Result<Unit> =
        try {
            when (validate(task)) {
                EMPTY_TITLE -> Result.failure(TaskException.EmptyTitle)
                INVALID_TITLE -> Result.failure(TaskException.InvalidTitle)
                SUCCESS -> insert(task)
            }
        } catch (exception: Exception) {
            Result.failure(TaskException.SaveFailed)
        }

    private fun insert(task: Task): Result<Unit> {
        store.save(task.withTrimmedValues())
        return Result.success(Unit)
    }

    private fun Task.withTrimmedValues(): Task = copy(title = title.trim())
}


