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

package com.taskodoro.android.app.tasks.create

import com.taskodoro.android.R
import com.taskodoro.android.app.helpers.expectEquals
import com.taskodoro.tasks.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Test

class CreateTaskViewModelTest {

    @Test
    fun init_doesNotModifyInitialState() {
        val (sut, _) = makeSUT()

        Assert.assertEquals(CreateTaskUIState(), sut.state.value)
    }

    @Test
    fun onTitleChanged_updatesTitleState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(title = "Hello"),
            CreateTaskUIState(title = "Hello, World!"),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf({
                sut.onTitleChanged("Hello")
            }, {
                sut.onTitleChanged("Hello, World!")
            }),
        )
    }

    @Test
    fun onDescriptionChanged_updatesDescriptionState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(description = "Hello"),
            CreateTaskUIState(description = "Hello, World!"),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf({
                sut.onDescriptionChanged("Hello")
            }, {
                sut.onDescriptionChanged("Hello, World!")
            }),
        )
    }

    @Test
    fun onPriorityChanged_updatesPriorityState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(priority = 0),
            CreateTaskUIState(priority = 1),
            CreateTaskUIState(priority = 2),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf({
                sut.onPriorityChanged(0)
            }, {
                sut.onPriorityChanged(1)
            }, {
                sut.onPriorityChanged(2)
            }),
        )
    }

    @Test
    fun save_emitsCorrectStatesOnSuccessfulSave() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(isTaskCreated = true),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                repository.completeSuccessfully()
                sut.create()
            },
        )
    }

    @Test
    fun save_emitsEmptyTitleErrorOnEmptyTitleValidationError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(titleError = R.string.create_new_task_empty_title_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                repository.completeWithError(TaskRepository.TaskException.EmptyTitle)
                sut.create()
            },
        )
    }

    @Test
    fun save_emitsInvalidTitleErrorOnInvalidTitleValidationError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(titleError = R.string.create_new_task_invalid_title_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                repository.completeWithError(TaskRepository.TaskException.InvalidTitle)
                sut.create()
            },
        )
    }

    @Test
    fun save_emitsUnknownErrorOnSaveFailedError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = R.string.create_new_task_unknown_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                repository.completeWithError(TaskRepository.TaskException.SaveFailed)
                sut.create()
            },
        )
    }

    @Test
    fun save_emitsUnknownErrorOnCaughtError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = R.string.create_new_task_unknown_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                repository.throwError()
                sut.create()
            },
        )
    }

    @Test
    fun save_clearsErrorWhenSavingCorrectlyAfterError() {
        val (sut, repository) = makeSUT()
        val expectedStatesForUnknownError = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = R.string.create_new_task_unknown_error),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(titleError = R.string.create_new_task_empty_title_error),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(isTaskCreated = true),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStatesForUnknownError,
            actions = listOf({
                repository.throwError()
                sut.create()
            }, {
                repository.completeWithError(TaskRepository.TaskException.EmptyTitle)
                sut.create()
            }, {
                repository.completeSuccessfully()
                sut.create()
            }),
        )
    }

    // region Helpers

    private fun makeSUT(): Pair<CreateTaskViewModel, RepositoryStub> {
        val repository = RepositoryStub()
        val sut = CreateTaskViewModel(
            createTask = { repository.save() },
            scope = CoroutineScope(Dispatchers.Main + SupervisorJob()),
        )

        return sut to repository
    }

    private fun anyTitle() = "any title"
    private fun anyDescription() = "any description"
    private fun anyPriority() = 1

    private class RepositoryStub {

        private var result: Result<Unit>? = null
        private var shouldThrowError = false

        fun save(): Flow<Result<Unit>> = flow {
            if (shouldThrowError) {
                shouldThrowError = false
                throw Exception()
            }
            emit(result!!)
        }

        fun completeSuccessfully() {
            result = Result.success(Unit)
        }

        fun completeWithError(error: Throwable) {
            result = Result.failure(error)
        }

        fun throwError() {
            shouldThrowError = true
        }
    }

    // endregion
}
