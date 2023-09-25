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

package com.taskodoro.tasks.create

import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.model.Task
import com.taskodoro.tasks.validator.TaskValidatorError
import com.taskodoro.tasks.validator.Validator

interface TaskCreateUseCase {
    object SaveFailed : Exception()

    operator fun invoke(
        title: String,
        description: String? = null,
        dueDate: Long? = null,
    ): Result<Unit>
}

class TaskCreate(
    private val repository: TaskRepository,
    private val validator: Validator<Task>,
    private val now: () -> Long,
) : TaskCreateUseCase {

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
            repository.save(task)
            Result.success(Unit)
        } catch (error: TaskValidatorError) {
            Result.failure(error)
        } catch (exception: TaskRepository.SaveFailed) {
            throw TaskCreateUseCase.SaveFailed
        }
    }
}
