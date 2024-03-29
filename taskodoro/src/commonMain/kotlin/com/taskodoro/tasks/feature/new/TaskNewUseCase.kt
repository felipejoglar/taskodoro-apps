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
import kotlinx.datetime.LocalDateTime

interface TaskNewUseCase {
    suspend operator fun invoke(
        title: String,
        description: String? = null,
        dueDate: LocalDateTime? = null,
    ): Result<Unit>
}

class TaskNew(
    private val saver: TaskSaver,
    private val validator: Validator<Task>,
    private val now: () -> LocalDateTime,
) : TaskNewUseCase {

    override suspend operator fun invoke(
        title: String,
        description: String?,
        dueDate: LocalDateTime?,
    ): Result<Unit> {
        val now = now()
        val task = Task(
            title = title.trim(),
            description = description,
            dueDate = dueDate ?: now,
            createdAt = now,
            updatedAt = now
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
