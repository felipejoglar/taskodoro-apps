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

package com.taskodoro.tasks.validator

import com.taskodoro.helpers.anyTask
import com.taskodoro.tasks.feature.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.fail

class TaskValidatorTest {

    @Test
    fun validate_throwsInvalidTitleOnEmptyTitle() {
        val emptyTitleTask = taskWith(title = "")
        val blankTitleTask = taskWith(title = "    ")
        val sut = makeSUT()

        val expectedError = TaskValidatorError.InvalidTitle

        assertFailsWith(expectedError::class) { sut.validate(emptyTitleTask) }
        assertFailsWith(expectedError::class) { sut.validate(blankTitleTask) }
    }

    @Test
    fun validate_throwsInvalidTitleOnInvalidTitle() {
        val invalidTitleTask = taskWith(title = "12")
        val invalidTitleTask1 = taskWith(title = "123")
        val invalidTitleTask2 = taskWith(title = "   123   ")
        val sut = makeSUT()

        val expectedError = TaskValidatorError.InvalidTitle

        assertFailsWith(expectedError::class) { sut.validate(invalidTitleTask) }
        assertFailsWith(expectedError::class) { sut.validate(invalidTitleTask1) }
        assertFailsWith(expectedError::class) { sut.validate(invalidTitleTask2) }
    }

    @Test
    fun validate_succeedsOnValidTitle() {
        val validTitleTask = taskWith(title = "1234")
        val validTitleTask1 = taskWith(title = "A valid title")
        val validTitleTask2 = taskWith(title = "    1234    ")
        val sut = makeSUT()

        assertNotFails { sut.validate(validTitleTask) }
        assertNotFails { sut.validate(validTitleTask1) }
        assertNotFails { sut.validate(validTitleTask2) }
    }

    @Test
    fun validate_throwsInvalidDueDateOnOneDayPreviousCurrentDay() {
        val yesterdayDueDateTask = taskWith(dueDate = yesterday)
        val sut = makeSUT()

        val expectedError = TaskValidatorError.InvalidDueDate

        assertFailsWith(expectedError::class) { sut.validate(yesterdayDueDateTask) }
    }

    @Test
    fun validate_succeedsOnCurrentDay() {
        val nowDueDateTask = taskWith(dueDate = today)
        val sut = makeSUT()

        assertNotFails { sut.validate(nowDueDateTask) }
    }

    @Test
    fun validate_succeedsOnOneDayPastCurrentDay() {
        val tomorrowDueDateTask = taskWith(dueDate = tomorrow)
        val sut = makeSUT()

        assertNotFails { sut.validate(tomorrowDueDateTask) }
    }

    // region Helpers

    private fun makeSUT(): Validator<Task> {
        return TaskValidatorFactory.create()
    }

    private fun taskWith(title: String) =
        anyTask(title = title, dueDate = today)

    private fun taskWith(dueDate: Long) =
        anyTask(dueDate = dueDate)

    private fun assertNotFails(block: () -> Unit) {
        try {
            block()
        } catch (exception: Exception) {
            fail("Expected to complete successfully, but failed with $exception instead")
        }
    }

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
