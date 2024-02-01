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

package com.taskodoro.tasks.data.local

import com.taskodoro.helpers.anyTask
import com.taskodoro.storage.db.TaskodoroDB
import com.taskodoro.storage.db.test.TestDriverFactory
import com.taskodoro.tasks.feature.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@OptIn(ExperimentalCoroutinesApi::class)
class SQLDelightTaskStoreTest {

    @Test
    fun save_failsOnInsertionFailure() = runTest {
        val sut = makeSUT()
        val task = anyTask()
        sut.save(task)

        assertFails {
            val duplicatedTask = task
            sut.save(duplicatedTask)
        }
    }

    @Test
    fun load_succeedsAfterSuccessfulInsertion() = runTest {
        val sut = makeSUT()
        val task = anyTask()
        val anotherTask = anyTask()

        sut.save(anotherTask)
        sut.save(task)

        val receivedTasks = mutableListOf<List<Task>>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            sut.load().toList(receivedTasks)
        }

        val expectedOrderedTasks = listOf(task, anotherTask)
        assertEquals(expectedOrderedTasks, receivedTasks.first())
    }

    // region Helpers

    private fun makeSUT(): SQLDelightTaskStore {
        val driver = TestDriverFactory.create()
        val db = TaskodoroDB(driver)
        db.clear()

        return SQLDelightTaskStore(database = db, dispatcher = UnconfinedTestDispatcher())
    }

    private fun TaskodoroDB.clear() = taskdoroDBQueries.clearDB()

    // endregion
}
