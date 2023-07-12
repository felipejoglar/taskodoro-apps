/*
 *    Copyright 2023 Felipe Joglar
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
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskValidatorTest {

    @Test
    fun validate_failsWithEmptyTitleOnEmptyTitle() {
        val emptyTitleTask = makeTaskWithTitle("")
        val emptyTitleTask1 = makeTaskWithTitle("   ")

        val validationResult = TaskValidator.validate(emptyTitleTask)
        val validationResult1 = TaskValidator.validate(emptyTitleTask1)

        assertEquals(TaskValidationResult.EMPTY_TITLE, validationResult)
        assertEquals(TaskValidationResult.EMPTY_TITLE, validationResult1)
    }

    @Test
    fun validate_failsWithInvalidTitleOnInvalidTitle() {
        val invalidTitleTask = makeTaskWithTitle("12")
        val invalidTitleTask1 = makeTaskWithTitle("123")
        val invalidTitleTask2 = makeTaskWithTitle("   123   ")

        val validationResult = TaskValidator.validate(invalidTitleTask)
        val validationResult1 = TaskValidator.validate(invalidTitleTask1)
        val validationResult2 = TaskValidator.validate(invalidTitleTask2)

        assertEquals(TaskValidationResult.INVALID_TITLE, validationResult)
        assertEquals(TaskValidationResult.INVALID_TITLE, validationResult1)
        assertEquals(TaskValidationResult.INVALID_TITLE, validationResult2)
    }

    @Test
    fun validate_succeedsOnValidTitle() {
        val validTitleTask = makeTaskWithTitle("1234")
        val validTitleTask1 = makeTaskWithTitle("A valid title")
        val validTitleTask2 = makeTaskWithTitle("    1234    ")

        val validationResult = TaskValidator.validate(validTitleTask)
        val validationResult1 = TaskValidator.validate(validTitleTask1)
        val validationResult2 = TaskValidator.validate(validTitleTask2)

        assertEquals(TaskValidationResult.SUCCESS, validationResult)
        assertEquals(TaskValidationResult.SUCCESS, validationResult1)
        assertEquals(TaskValidationResult.SUCCESS, validationResult2)
    }

    // region Helpers

    private fun makeTaskWithTitle(title: String): Task =
        Task(
            id = "An id",
            title = title,
            createdAt = 0,
        )

    // endregion
}
