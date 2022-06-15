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

package com.taskodoro.storage.tasks

import com.taskodoro.storage.tasks.helpers.TaskStoreSpy
import com.taskodoro.storage.tasks.helpers.anyTask
import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.model.TaskValidationResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LocalTaskRepositoryTest {

    @Test
    fun init_doesNotMessageStoreOnCreation() {
        val (_, store, _) = makeSUT()

        assertEquals(emptyList(), store.messages)
    }

    @Test
    fun save_failsWithValidationTypeOnValidationError() {
        val (sut, _, validator) = makeSUT()
        val task = anyTask()

        validator.completeValidationWithFailure()
        val result = sut.save(task)

        assertTrue(result.exceptionOrNull() is TaskRepository.TaskValidationException)
        assertTrue((result.exceptionOrNull() as TaskRepository.TaskValidationException).validationResult != TaskValidationResult.SUCCESS)
    }

    @Test
    fun save_failsOnInsertionError() {
        val (sut, store, validator) = makeSUT()
        val task = anyTask()

        validator.completeValidationSuccessfully()
        store.completeInsertionWithFailure()
        val result = sut.save(task)

        assertEquals(Result.failure(TaskRepository.TaskInsertionException), result)
    }

    @Test
    fun save_succeedsOnSuccessfulInsertion() {
        val (sut, store, validator) = makeSUT()
        val task = anyTask()

        validator.completeValidationSuccessfully()
        store.completeInsertionSuccessfully()
        val result = sut.save(task)

        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun save_succeedsWithTrimmedValuesOnSuccessfulInsertion() {
        val (sut, store, validator) = makeSUT()
        val task = anyTask().copy(title = "   A title   ")

        validator.completeValidationSuccessfully()
        store.completeInsertionSuccessfully()
        val result = sut.save(task)

        assertEquals(Result.success(Unit), result)
        assertEquals("A title", store.savedTasks.first().title)
    }

    // region Helpers

    private fun makeSUT(): TestObjects {
        val store = TaskStoreSpy()
        val validator = TaskValidatorStub()
        val sut = LocalTaskRepository(
            store = store,
            validate = { validator.validate() }
        )

        return TestObjects(sut, store, validator)
    }

    private data class TestObjects(
        val sut: LocalTaskRepository,
        val store: TaskStoreSpy,
        val validator: TaskValidatorStub,
    )

    private class TaskValidatorStub {
        private var validationResult: TaskValidationResult? = null

        fun validate(): TaskValidationResult = validationResult!!

        fun completeValidationSuccessfully() {
            validationResult = TaskValidationResult.SUCCESS
        }

        fun completeValidationWithFailure() {
            validationResult = TaskValidationResult.INVALID_TITLE
        }
    }
    // endregion
}