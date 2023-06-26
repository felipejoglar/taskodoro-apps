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

package com.taskodoro.storage.tasks.store

import com.taskodoro.storage.db.DriverFactory
import com.taskodoro.storage.db.TaskodoroDB
import com.taskodoro.storage.tasks.helpers.anyTask
import com.taskodoro.tasks.model.Task
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class SQLDelightTaskStoreTest {

    @Test
    fun save_succeedsOnSuccessfulInsertion() {
        val sut = makeSUT()
        val task = anyTask()

        sut.save(task)

        assertEquals(task, sut.load().first())
    }

    @Test
    fun save_failsOnInsertionFailure() {
        val sut = makeSUT()
        val task = anyTask()
        sut.save(task)

        assertFails { sut.save(task) }
    }

    // region Helpers

    private fun makeSUT(): SQLDelightTaskStore {
        val driver = DriverFactory().createDriver()
        val db = TaskodoroDB(driver)
        db.clear()

        return SQLDelightTaskStore(database = db)
    }

    private fun TaskodoroDB.clear() = taskdoroDBQueries.clearDB()

    private fun SQLDelightTaskStore.load() = tasksQueries.load()
        .executeAsList()
        .map {
            Task(
                id = it.id,
                title = it.title,
                createdAt = it.createdAt
            )
        }

    // endregion
}