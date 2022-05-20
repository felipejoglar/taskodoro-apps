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

import com.taskodoro.storage.db.TaskodoroDB
import com.taskodoro.storage.tasks.helpers.anyTask
import com.taskodoro.storage.tasks.helpers.makeTestDriver
import com.taskodoro.storage.tasks.store.SQLDelightTaskStore
import com.taskodoro.tasks.model.Task
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class SQLDelightTaskStoreTest {

    @Test
    fun save_succeedsOnSuccessfulInsertion() {
        val sut = makeSUT()
        val anyTask = anyTask()

        sut.save(anyTask)

        assertEquals(anyTask, sut.load().first())
    }

    @Test
    fun save_failsOnInsertionFailure() {
        val sut = makeSUT()
        val anyTask = anyTask()
        sut.save(anyTask)

        assertFails {
            sut.save(anyTask)
        }
    }

    // region Helpers

    private fun makeSUT(): SQLDelightTaskStore {
        val driver = makeTestDriver()
        val db = TaskodoroDB(driver)
        db.clear()

        return SQLDelightTaskStore(database = db)
    }

    private fun TaskodoroDB.clear() = taskdoroDBQueries.clearDB()

    private fun SQLDelightTaskStore.load() = tasksQueries.load()
        .executeAsList()
        .map {
            Task(id = it.id.toLong(), title = it.description)
        }

    // endregion
}