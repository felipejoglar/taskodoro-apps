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

package com.taskodoro.tasks.validator

import com.taskodoro.helpers.anyTask
import com.taskodoro.tasks.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskValidatorTest {

    @Test
    fun validate_failsWithEmptyAndInvalidTitleOnEmptyTitle() {
        val emptyTitleTask = taskWith(title = "")
        val blankTitleTask = taskWith(title = "    ")
        val sut = makeSUT()

        val emptyValidation = sut.validate(emptyTitleTask)
        val blankValidation = sut.validate(blankTitleTask)

        val expectedError = listOf(
            TaskValidatorError.Title.Empty,
            TaskValidatorError.Title.Invalid,
        )
        assertEquals(expectedError, emptyValidation)
        assertEquals(expectedError, blankValidation)
    }

    @Test
    fun validate_failsWithInvalidTitleOnInvalidTitle() {
        val invalidTitleTask = taskWith(title = "12")
        val invalidTitleTask1 = taskWith(title = "123")
        val invalidTitleTask2 = taskWith(title = "   123   ")
        val sut = makeSUT()

        val validation = sut.validate(invalidTitleTask)
        val validation1 = sut.validate(invalidTitleTask1)
        val validation2 = sut.validate(invalidTitleTask2)

        val expectedError = listOf(TaskValidatorError.Title.Invalid)
        assertEquals(expectedError, validation)
        assertEquals(expectedError, validation1)
        assertEquals(expectedError, validation2)
    }

    @Test
    fun validate_succeedsOnValidTitle() {
        val validTitleTask = taskWith(title = "1234")
        val validTitleTask1 = taskWith(title = "A valid title")
        val validTitleTask2 = taskWith(title = "    1234    ")
        val sut = makeSUT()

        val validationResult = sut.validate(validTitleTask)
        val validationResult1 = sut.validate(validTitleTask1)
        val validationResult2 = sut.validate(validTitleTask2)

        val expectedErrors = listOf<ValidatorError>()
        assertEquals(expectedErrors, validationResult)
        assertEquals(expectedErrors, validationResult1)
        assertEquals(expectedErrors, validationResult2)
    }

    @Test
    fun validate_failsWithInvalidDueDateOnOneDayPreviousCurrentDay() {
        val yesterdayDueDateTask = taskWith(dueDate = yesterday)
        val sut = makeSUT()

        val validation = sut.validate(yesterdayDueDateTask)

        val expectedError = listOf(TaskValidatorError.DueDate.Invalid)
        assertEquals(expectedError, validation)
    }

    @Test
    fun validate_succeedsOnCurrentDay() {
        val nowDueDateTask = taskWith(dueDate = today)
        val sut = makeSUT()

        val validation = sut.validate(nowDueDateTask)

        val expectedError = listOf<ValidatorError>()
        assertEquals(expectedError, validation)
    }

    @Test
    fun validate_succeedsOnOneDayPastCurrentDay() {
        val tomorrowDueDateTask = taskWith(dueDate = tomorrow)
        val sut = makeSUT()

        val validation = sut.validate(tomorrowDueDateTask)

        val expectedError = listOf<ValidatorError>()
        assertEquals(expectedError, validation)
    }

    @Test
    fun validate_failsAllValidations() {
        val invalidTask = anyTask(title = "", dueDate = yesterday)
        val sut = makeSUT()

        val emptyValidation = sut.validate(invalidTask)

        val expectedError = listOf(
            TaskValidatorError.Title.Empty,
            TaskValidatorError.Title.Invalid,
            TaskValidatorError.DueDate.Invalid,
        )
        assertEquals(expectedError, emptyValidation)
    }

    // region Helpers

    private fun makeSUT(): Validator<Task> {
        return TaskValidatorFactory.create()
    }

    private fun taskWith(title: String) =
        anyTask(title = title, dueDate = today)

    private fun taskWith(dueDate: Long) =
        anyTask(dueDate = dueDate)

    private val today: Long = Clock.System
        .now()
        .epochSeconds

    private val yesterday: Long = Clock.System
        .now()
        .minus(1, DateTimeUnit.DAY, TimeZone.UTC)
        .epochSeconds

    private val tomorrow: Long = Clock.System
        .now()
        .minus(-1, DateTimeUnit.DAY, TimeZone.UTC)
        .epochSeconds

    // endregion
}
