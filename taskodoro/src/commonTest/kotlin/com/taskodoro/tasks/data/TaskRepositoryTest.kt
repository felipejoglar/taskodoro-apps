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

package com.taskodoro.tasks.data

import com.taskodoro.helpers.anyTask
import com.taskodoro.tasks.feature.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryTest {

    @Test
    fun init_doesNotMessageStoreOnCreation() {
        val (_, store) = makeSUT()

        assertEquals(emptyList(), store.messages)
    }

    @Test
    fun save_failsOnInsertionError() = runTest {
        val (sut, store) = makeSUT()
        val task = anyTask()

        store.completeInsertionWithFailure()
        val result = sut.save(task)

        assertEquals(Result.failure(TaskRepository.SaveFailed), result)
    }

    @Test
    fun save_succeedsOnSuccessfulInsertion() = runTest {
        val (sut, store) = makeSUT()
        val task = anyTask()

        store.completeInsertionSuccessfully()
        val result = sut.save(task)

        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun load_failsOnRetrievalError() = runTest {
        val retrievalError = Exception()
        val (sut, store) = makeSUT()

        store.completeRetrievalWithError(retrievalError)

        assertFails { sut.load().collect() }
    }

    @Test
    fun load_succeedsOnSuccessfulRetrieval() = runTest {
        val (sut, store) = makeSUT()

        val receivedTasks = mutableListOf<List<Task>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.load().toList(receivedTasks)
        }
        store.completeRetrievalSuccessfullyWith(emptyList())

        assertEquals(emptyList(), receivedTasks.first())
    }

    @Test
    fun load_succeedsWithTasksOnSuccessfulRetrieval() = runTest {
        val tasks = makeTasks()
        val (sut, store) = makeSUT()

        val receivedTasks = mutableListOf<List<Task>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.load().toList(receivedTasks)
        }
        store.completeRetrievalSuccessfullyWith(tasks)

        assertEquals(tasks, receivedTasks.first())
    }

    // region Helpers

    private fun makeSUT(): Pair<TaskRepository, TaskStoreSpy> {
        val store = TaskStoreSpy()
        val sut = TaskRepository(store = store)

        return sut to store
    }

    private fun makeTasks() = List(3) {
        Task(title = "Task $it", dueDate = 0, createdAt = 0)
    }

    private class TaskStoreSpy : TaskStore {
        enum class Message {
            SAVE, LOAD,
        }

        var messages = mutableListOf<Message>()
            private set
        var savedTasks = mutableListOf<Task>()
            private set

        private var insertionSuccessful = true

        override suspend fun save(task: Task) {
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

        private val flow = MutableSharedFlow<List<Task>>()
        private var retrievalError: Exception? = null

        override fun load(): Flow<List<Task>> {
            messages.add(Message.LOAD)

            retrievalError?.let { throw it }
            return flow
        }

        fun completeRetrievalWithError(error: Exception) {
            retrievalError = error
        }

        suspend fun completeRetrievalSuccessfullyWith(tasks: List<Task>) {
            flow.emit(tasks)
        }
    }

    // endregion
}
