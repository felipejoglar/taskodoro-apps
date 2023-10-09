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

package com.taskodoro.storage.tasks

import com.taskodoro.helpers.anyTask
import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.model.Task
import kotlin.test.Test
import kotlin.test.assertEquals

class LocalTaskRepositoryTest {

    @Test
    fun init_doesNotMessageStoreOnCreation() {
        val (_, store) = makeSUT()

        assertEquals(emptyList(), store.messages)
    }

    @Test
    fun save_failsOnInsertionError() {
        val (sut, store) = makeSUT()
        val task = anyTask()

        store.completeInsertionWithFailure()
        val result = sut.save(task)

        assertEquals(Result.failure(TaskRepository.SaveFailed), result)
    }

    @Test
    fun save_succeedsOnSuccessfulInsertion() {
        val (sut, store) = makeSUT()
        val task = anyTask()

        store.completeInsertionSuccessfully()
        val result = sut.save(task)

        assertEquals(Result.success(Unit), result)
    }

    // region Helpers

    private fun makeSUT(): Pair<TaskRepository, TaskStoreSpy> {
        val store = TaskStoreSpy()
        val sut = LocalTaskRepository(store = store)

        return sut to store
    }

    private class TaskStoreSpy : TaskStore {
        enum class Message {
            SAVE,
        }

        var messages = mutableListOf<Message>()
            private set
        var savedTasks = mutableListOf<Task>()
            private set

        private var insertionSuccessful = true

        override fun save(task: Task) {
            messages.add(Message.SAVE)
            savedTasks.add(task)

            if (!insertionSuccessful) throw Exception()
        }

        fun completeInsertionSuccessfully() {
            insertionSuccessful = true
        }

        fun completeInsertionWithFailure() {
            insertionSuccessful = false
        }
    }

    // endregion
}
