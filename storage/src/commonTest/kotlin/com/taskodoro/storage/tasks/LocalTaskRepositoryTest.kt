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
import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.model.Task
import kotlin.test.Test
import kotlin.test.assertEquals

class LocalTaskRepositoryTest {

    @Test
    fun init_doesNotMessageStoreOnCreation() {
        val (_, store, _) = makeSUT()

        assertEquals(emptyList(), store.messages)
    }

    @Test
    fun save_failsOnValidationError() {
        val (sut, _, validator) = makeSUT()
        val task = anyTask()

        validator.completeValidationWithFailure()
        val result = sut.save(task)

        assertEquals(Result.failure(TaskRepository.TaskValidationException), result)
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

    private fun anyTask() = Task(
        id = 1,
        title = "A task title"
    )

    private data class TestObjects(
        val sut: LocalTaskRepository,
        val store: TaskStoreSpy,
        val validator: TaskValidatorStub,
    )

    private class TaskValidatorStub {
        private var isValid: Boolean? = null

        fun validate(): Boolean = isValid!!

        fun completeValidationSuccessfully() {
            isValid = true
        }

        fun completeValidationWithFailure() {
            isValid = false
        }
    }
    // endregion
}