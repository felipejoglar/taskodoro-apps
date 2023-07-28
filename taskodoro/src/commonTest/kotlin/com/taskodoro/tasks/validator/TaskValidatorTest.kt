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
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskValidatorTest {

    @Test
    fun validate_failsWithEmptyAndInvalidTitleOnEmptyTitle() {
        val emptyTitleTask = anyTask(title = "")
        val blankTitleTask = anyTask(title = "    ")
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
        val invalidTitleTask = anyTask(title = "12")
        val invalidTitleTask1 = anyTask(title = "123")
        val invalidTitleTask2 = anyTask(title = "   123   ")
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
        val validTitleTask = anyTask(title = "1234")
        val validTitleTask1 = anyTask(title = "A valid title")
        val validTitleTask2 = anyTask(title = "    1234    ")
        val sut = makeSUT()

        val validationResult = sut.validate(validTitleTask)
        val validationResult1 = sut.validate(validTitleTask1)
        val validationResult2 = sut.validate(validTitleTask2)

        val expectedErrors = listOf<ValidatorError>()
        assertEquals(expectedErrors, validationResult)
        assertEquals(expectedErrors, validationResult1)
        assertEquals(expectedErrors, validationResult2)
    }

    // region Helpers

    private fun makeSUT(): TaskValidator {
        val validators = listOf(
            EmptyTitleValidator(),
            TitleLengthValidator(minimumTitleLength),
        )

        return TaskValidator(validators)
    }

    private val minimumTitleLength = 4

    // endregion
}
