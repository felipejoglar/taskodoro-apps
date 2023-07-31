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

package com.taskodoro.tasks

import com.taskodoro.tasks.model.Task
import com.taskodoro.tasks.validator.Validator
import com.taskodoro.tasks.validator.ValidatorError

interface CreateTaskUseCase {
    sealed class Result {
        object Success : Result()
        data class Failure(val errors: List<ValidatorError>) : Result()
    }

    object SaveFailed : Exception()

    operator fun invoke(task: Task): Result
}

class CreateTask(
    private val repository: TaskRepository,
    private val validator: Validator<Task>,
) : CreateTaskUseCase {

    override operator fun invoke(task: Task): CreateTaskUseCase.Result {
        try {
            val errors = validator.validate(task.withTrimmedValues())

            return if (errors.isEmpty()) {
                repository.save(task)
                CreateTaskUseCase.Result.Success
            } else {
                CreateTaskUseCase.Result.Failure(errors)
            }
        } catch (exception: TaskRepository.SaveFailed) {
            throw CreateTaskUseCase.SaveFailed
        }
    }

    private fun Task.withTrimmedValues(): Task = copy(title = title.trim())
}
