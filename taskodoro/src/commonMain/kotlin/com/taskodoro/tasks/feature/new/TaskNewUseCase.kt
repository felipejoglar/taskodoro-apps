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

package com.taskodoro.tasks.feature.new

import com.taskodoro.tasks.feature.TaskSaver
import com.taskodoro.tasks.feature.model.Task
import com.taskodoro.tasks.validator.TaskValidatorError
import com.taskodoro.tasks.validator.Validator

interface TaskNewUseCase {
    operator fun invoke(
        title: String,
        description: String? = null,
        dueDate: Long? = null,
    ): Result<Unit>
}

class TaskNew(
    private val saver: TaskSaver,
    private val validator: Validator<Task>,
    private val now: () -> Long,
) : TaskNewUseCase {

    override operator fun invoke(
        title: String,
        description: String?,
        dueDate: Long?,
    ): Result<Unit> {
        val task = Task(
            title = title.trim(),
            description = description,
            dueDate = dueDate ?: now(),
            createdAt = now(),
        )

        return try {
            validator.validate(task)
            saver.save(task)
            Result.success(Unit)
        } catch (error: TaskValidatorError) {
            Result.failure(error)
        }
    }
}
