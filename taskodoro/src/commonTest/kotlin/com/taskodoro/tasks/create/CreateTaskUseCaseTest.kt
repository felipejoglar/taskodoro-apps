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
import com.taskodoro.tasks.validator.ValidatorError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateTaskUseCaseTest {

    @Test
    fun save_failsWithInvalidTitleTaskExceptionOnInvalidTitleFailure() {
        val (sut, _, validator) = makeSUT()

        validator.completeWithInvalidTitleFailure()
        val result = sut.invoke(anyTitle())

        val expectedResult = CreateTaskUseCase.Result.Failure(
            errors = listOf(TaskValidatorError.Title.Invalid),
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun save_failsWithEmptyTitleTaskExceptionOnEmptyTitleFailure() {
        val (sut, _, validator) = makeSUT()

        validator.completeWithEmptyTitleFailure()
        val result = sut.invoke(anyTitle())

        val expectedResult = CreateTaskUseCase.Result.Failure(
            errors = listOf(TaskValidatorError.Title.Empty),
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun save_failsWithSaveFailedExceptionOnInsertionFailure() {
        val (sut, repository, validator) = makeSUT()

        validator.completeSuccessfully()
        repository.completeSavingWithFailure()

        assertFailsWith(CreateTaskUseCase.SaveFailed::class) {
            sut.invoke(anyTitle())
        }
    }

    @Test
    fun save_succeedsOnSuccessfulInsertion() {
        val (sut, repository, validator) = makeSUT()

        validator.completeSuccessfully()
        repository.completeSavingSuccessfully()
        val result = sut.invoke(anyTitle())

        assertEquals(CreateTaskUseCase.Result.Success, result)
    }

    // region Helpers

    private fun makeSUT(): Triple<CreateTaskUseCase, TaskRepositoryStub, TaskValidatorStub> {
        val validator = TaskValidatorStub()
        val repository = TaskRepositoryStub()
        val sut = CreateTask(
            repository = repository,
            validator = validator,
            now = { 0 },
        )

        return Triple(sut, repository, validator)
    }

    private fun anyTitle() = "A task"

    private class TaskRepositoryStub : TaskRepository {
        private var result: Result<Unit>? = null
        private var shouldThrow = false

        override fun save(task: Task): Result<Unit> {
            if (shouldThrow) {
                shouldThrow = false
                throw TaskRepository.SaveFailed
            }
            return result!!
        }

        fun completeSavingSuccessfully() {
            result = Result.success(Unit)
        }

        fun completeSavingWithFailure() {
            shouldThrow = true
        }
    }

    private class TaskValidatorStub : Validator<Task> {
        private var validatorErrors: MutableList<ValidatorError> = mutableListOf()

        override fun validate(value: Task): List<ValidatorError> {
            return validatorErrors
        }

        fun completeSuccessfully() {
            validatorErrors.clear()
        }

        fun completeWithEmptyTitleFailure() {
            validatorErrors.add(TaskValidatorError.Title.Empty)
        }

        fun completeWithInvalidTitleFailure() {
            validatorErrors.add(TaskValidatorError.Title.Invalid)
        }
    }

    // endregion
}
