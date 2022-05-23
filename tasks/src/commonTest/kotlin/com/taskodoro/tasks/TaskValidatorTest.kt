/*
 *    Copyright 2022 Felipe Joglar
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
import com.taskodoro.tasks.model.TaskValidation
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskValidatorTest {

    @Test
    fun validate_failsWithEmptyTitleOnEmptyTitle() {
        val sut = makeSUT()
        val emptyTitleTask = makeTaskWithTitle("")
        val emptyTitleTask1 = makeTaskWithTitle("   ")

        val validationResult = sut.validate(emptyTitleTask)
        val validationResult1 = sut.validate(emptyTitleTask1)

        assertEquals(TaskValidation.EMPTY_TITLE, validationResult)
        assertEquals(TaskValidation.EMPTY_TITLE, validationResult1)
    }

    @Test
    fun validate_failsWithInvalidTitleOnInvalidTitle() {
        val sut = makeSUT()
        val invalidTitleTask = makeTaskWithTitle("12")
        val invalidTitleTask1 = makeTaskWithTitle("123")
        val invalidTitleTask2 = makeTaskWithTitle("   123   ")

        val validationResult = sut.validate(invalidTitleTask)
        val validationResult1 = sut.validate(invalidTitleTask1)
        val validationResult2 = sut.validate(invalidTitleTask2)

        assertEquals(TaskValidation.INVALID_TITLE, validationResult)
        assertEquals(TaskValidation.INVALID_TITLE, validationResult1)
        assertEquals(TaskValidation.INVALID_TITLE, validationResult2)
    }

    @Test
    fun validate_succeedsOnValidTitle() {
        val sut = makeSUT()
        val validTitleTask = makeTaskWithTitle("1234")
        val validTitleTask1 = makeTaskWithTitle("A valid title")
        val validTitleTask2 = makeTaskWithTitle("    1234    ")

        val validationResult = sut.validate(validTitleTask)
        val validationResult1 = sut.validate(validTitleTask1)
        val validationResult2 = sut.validate(validTitleTask2)

        assertEquals(TaskValidation.SUCCESS, validationResult)
        assertEquals(TaskValidation.SUCCESS, validationResult1)
        assertEquals(TaskValidation.SUCCESS, validationResult2)
    }

    // region Helpers

    private fun makeSUT() = TaskValidator()

    private fun makeTaskWithTitle(title: String): Task =
        Task(
            id = "An id",
            title = title,
            createdAt = 0
        )

    // endregion
}