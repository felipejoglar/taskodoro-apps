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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class GetTasksTest {

    @Test
    fun getTasksSuccess() = runTest {
        val sut = makeSUT(TestBehaviour.Success())

        val tasks = sut().first()

        assertEquals(expectedTasks, tasks)
    }

    @Test
    fun getTasksSeveralEmissionsSuccess() = runTest {
        val sut = makeSUT(TestBehaviour.Success(multipleEmissions = true))
        val collectedTasks = mutableListOf<List<Task>>()

        sut().collect { tasks ->
            collectedTasks.add(tasks)
        }

        assertEquals(2, collectedTasks.count())
        assertEquals(expectedTasks, collectedTasks[0])
        assertEquals(expectedTasks.reversed(), collectedTasks[1])
    }

    @Test
    fun getTasksThrowsOnFailure() = runTest {
        val sut = makeSUT(TestBehaviour.Fail)

        assertFails { sut().collect() }
    }

    // region Helper

    private fun makeSUT(testBehaviour: TestBehaviour) = GetTasks(
        loadTasks = {
            flow {
                when (testBehaviour) {
                    is TestBehaviour.Success -> {
                        emit(expectedTasks)

                        if (testBehaviour.multipleEmissions) {
                            emit(expectedTasks.reversed())
                        }
                    }
                    TestBehaviour.Fail -> throw RuntimeException()
                }
            }
        }
    )

    private val expectedTasks = List(20) {
        Task(id = it.toLong(), title = "Task $it title")
    }

    private sealed class TestBehaviour {
        data class Success(val multipleEmissions: Boolean = false) : TestBehaviour()
        object Fail : TestBehaviour()
    }

    // endregion
}