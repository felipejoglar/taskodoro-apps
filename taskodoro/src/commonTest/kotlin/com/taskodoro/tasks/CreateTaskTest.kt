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
import com.taskodoro.tasks.model.TaskValidationResult
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateTaskTest {

    @Test
    fun save_failsWithInvalidTitleTaskExceptionOnInvalidTitleFailure() {
        val task = anyTask()

        validator.completeWithInvalidTitleFailure()
        val result = save(task)

        assertEquals(Result.failure(TaskRepository.TaskException.InvalidTitle), result)
    }

    @Test
    fun save_failsWithEmptyTitleTaskExceptionOnEmptyTitleFailure() {
        val task = anyTask()

        validator.completeWithEmptyTitleFailure()
        val result = save(task)

        assertEquals(Result.failure(TaskRepository.TaskException.EmptyTitle), result)
    }

    @Test
    fun save_failsWithSaveFailedExceptionOnInsertionFailure() {
        val task = anyTask()

        validator.completeSuccessfully()
        repository.completeSavingWithFailure()
        val result = save(task)

        assertEquals(Result.failure(TaskRepository.TaskException.SaveFailed), result)
    }

    @Test
    fun save_succeedsWithTrimmedValuesOnSuccessfulInsertion() {
        val task = anyTask().copy(title = "   A title   ")

        validator.completeSuccessfully()
        repository.completeSavingSuccessfully()
        val result = save(task)

        assertEquals(Result.success(Unit), result)
    }

    // region Helpers

    private lateinit var validator: TaskValidatorStub
    private lateinit var repository: TaskRepositoryStub

    @BeforeTest
    fun setupDependencies() {
        validator = TaskValidatorStub()
        repository = TaskRepositoryStub()
    }

    private fun save(task: Task) = save(task, repository) { validator.validate() }

    private fun anyTask() = Task(
        id = "An id",
        title = "A task title",
        createdAt = 0L,
    )

    private class TaskRepositoryStub : TaskRepository {

        private var result: Result<Unit>? = null
        private var shouldThrow = false

        override fun save(task: Task): Result<Unit> {
            if (shouldThrow) {
                shouldThrow = false
                throw TaskRepository.TaskException.SaveFailed
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

    private class TaskValidatorStub {
        private var validationResult: TaskValidationResult? = null

        fun validate(): TaskValidationResult = validationResult!!

        fun completeSuccessfully() {
            validationResult = TaskValidationResult.SUCCESS
        }

        fun completeWithEmptyTitleFailure() {
            validationResult = TaskValidationResult.EMPTY_TITLE
        }

        fun completeWithInvalidTitleFailure() {
            validationResult = TaskValidationResult.INVALID_TITLE
        }
    }

    // endregion
}
