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

package com.taskodoro.android.app.tasks.create

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
    fun save_emitsCorrectStatesOnSuccessfulSave() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(isTaskSaved = true)
        )

        expectEquals(sut.state, expectedStates) {
            repository.completeSuccessfully()
            sut.save(anyTitle())
        }
    }

    @Test
    fun save_emitsEmptyTitleErrorOnEmptyTitleValidationError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = CreateTaskUIState.Error.EmptyTitle)
        )

        expectEquals(sut.state, expectedStates) {
            repository.completeWithError(TaskRepository.TaskException.EmptyTitle)
            sut.save(anyTitle())
        }
    }

    @Test
    fun save_emitsInvalidTitleErrorOnInvalidTitleValidationError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = CreateTaskUIState.Error.InvalidTitle)
        )

        expectEquals(sut.state, expectedStates) {
            repository.completeWithError(TaskRepository.TaskException.InvalidTitle)
            sut.save(anyTitle())
        }
    }

    @Test
    fun save_emitsUnknownErrorOnSaveFailedError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = CreateTaskUIState.Error.Unknown)
        )

        expectEquals(sut.state, expectedStates) {
            repository.completeWithError(TaskRepository.TaskException.SaveFailed)
            sut.save(anyTitle())
        }
    }

    @Test
    fun save_emitsUnknownErrorOnCaughtError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = CreateTaskUIState.Error.Unknown)
        )

        expectEquals(sut.state, expectedStates) {
            repository.throwError()
            sut.save(anyTitle())
        }
    }

    @Test
    fun save_clearsErrorWhenSavingCorrectlyAfterError() {
        val (sut, repository) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = CreateTaskUIState.Error.Unknown),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(isTaskSaved = true)
        )

        expectEquals(sut.state, expectedStates) {
            repository.throwError()
            sut.save(anyTitle())

            repository.completeSuccessfully()
            sut.save(anyTitle())
        }
    }

    // region Helpers

    private fun makeSUT(): Pair<CreateTaskViewModel, RepositoryStub> {
        val repository = RepositoryStub()
        val sut = CreateTaskViewModel(
            saveTask = { repository.save() },
            scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        )

        return sut to repository
    }

    private fun anyTitle() = "any title"


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