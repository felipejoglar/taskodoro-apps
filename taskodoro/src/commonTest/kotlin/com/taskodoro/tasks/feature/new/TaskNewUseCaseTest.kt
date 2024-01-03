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

import com.taskodoro.tasks.feature.TaskRepository
import com.taskodoro.tasks.feature.new.TaskNew
import com.taskodoro.tasks.feature.new.TaskNewUseCase
import com.taskodoro.tasks.feature.model.Task
import com.taskodoro.tasks.validator.TaskValidatorError
import com.taskodoro.tasks.validator.Validator
import com.taskodoro.tasks.validator.ValidatorError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class TaskNewUseCaseTest {

    @Test
    fun save_failsWithInvalidTitleOnInvalidTitleFailure() {
        val (sut, _, validator) = makeSUT()

        validator.completeWithInvalidTitleFailure()
        val result = sut.invoke(anyTitle)

        val expectedResult = Result.failure<Unit>(TaskValidatorError.InvalidTitle)
        assertEquals(expectedResult, result)
    }

    @Test
    fun save_failsWithInvalidDueDateOnInvalidDueDateFailure() {
        val (sut, _, validator) = makeSUT()

        validator.completeWithInvalidDueDateFailure()
        val result = sut.invoke(anyTitle)

        val expectedResult = Result.failure<Unit>(TaskValidatorError.InvalidDueDate)
        assertEquals(expectedResult, result)
    }

    @Test
    fun save_failsWithExceptionOnInsertionFailure() {
        val (sut, repository, validator) = makeSUT()

        validator.completeSuccessfully()
        repository.completeSavingWithFailure()

        assertFailsWith(Exception::class) {
            sut.invoke(anyTitle)
        }
    }

    @Test
    fun save_succeedsOnSuccessfulInsertion() {
        val (sut, repository, validator) = makeSUT()

        validator.completeSuccessfully()
        repository.completeSavingSuccessfully()
        val result = sut.invoke(anyTitle)

        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun save_savesCorrectTitleTrimmed() {
        val (sut, repository, validator) = makeSUT()
        val title = "  A title   "

        validator.completeSuccessfully()
        repository.completeSavingSuccessfully()

        sut.invoke(title)
        assertEquals("A title", repository.savedTask.title)

        sut.invoke("A title")
        assertEquals("A title", repository.savedTask.title)
    }

    @Test
    fun save_savesCorrectDescription() {
        val (sut, repository, validator) = makeSUT()
        val description = "A description"

        validator.completeSuccessfully()
        repository.completeSavingSuccessfully()

        sut.invoke(title = anyTitle, description = description)
        assertEquals(description, repository.savedTask.description)

        sut.invoke(title = anyTitle, description = null)
        assertNull(repository.savedTask.description)
    }

    @Test
    fun save_savesCorrectDueDate() {
        val now = 0L
        val dueDate = 100L
        val (sut, repository, validator) = makeSUT(now)

        validator.completeSuccessfully()
        repository.completeSavingSuccessfully()

        sut.invoke(title = anyTitle, dueDate = dueDate)
        assertEquals(dueDate, repository.savedTask.dueDate)

        sut.invoke(title = anyTitle, description = null)
        assertEquals(now, repository.savedTask.dueDate)
    }

    // region Helpers

    private fun makeSUT(
        now: Long = 0,
    ): Triple<TaskNewUseCase, TaskRepositoryStub, TaskValidatorStub> {
        val validator = TaskValidatorStub()
        val repository = TaskRepositoryStub()
        val sut = TaskNew(
            repository = repository,
            validator = validator,
            now = { now },
        )

        return Triple(sut, repository, validator)
    }

    private val anyTitle = "A task"

    private class TaskRepositoryStub : TaskRepository {
        private var result: Result<Unit>? = null
        private var shouldThrow = false

        lateinit var savedTask: Task
            private set

        override fun save(task: Task): Result<Unit> {
            savedTask = task
            if (shouldThrow) {
                shouldThrow = false
                throw Exception()
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
        private var error: ValidatorError? = null

        override fun validate(value: Task) {
            error?.let { throw it }
        }

        fun completeSuccessfully() {
            error = null
        }

        fun completeWithInvalidTitleFailure() {
            error = TaskValidatorError.InvalidTitle
        }

        fun completeWithInvalidDueDateFailure() {
            error = TaskValidatorError.InvalidDueDate
        }
    }

    // endregion
}
