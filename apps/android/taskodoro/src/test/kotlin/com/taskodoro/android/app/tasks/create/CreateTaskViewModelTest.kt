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

import com.taskodoro.android.app.helpers.MainDispatcherRule
import com.taskodoro.android.app.helpers.test
import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.model.TaskValidationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class CreateTaskViewModelTest {

    @get:Rule
    var coroutinesRule = MainDispatcherRule()

    @Test
    fun init_doesNotModifyInitialState() {
        val (sut, _) = makeSUT()

        Assert.assertEquals(CreateTaskUIState(), sut.state.value)
    }

    @Test
    fun save_emitsCorrectStatesOnSuccessfulSave() = runTest {
        val (sut, repository) = makeSUT()

        test(sut.state) { states ->

            repository.completeSuccessfully()
            sut.save(anyTitle())

            runCurrent()

            Assert.assertEquals(
                listOf(
                    CreateTaskUIState(),
                    CreateTaskUIState(loading = true),
                    CreateTaskUIState(isTaskSaved = true)
                ),
                states
            )
        }
    }

    @Test
    fun save_emitsInsertionErrorOnInsertionError() = runTest {
        val (sut, repository) = makeSUT()

        test(sut.state) { states ->

            repository.completeWithError(TaskRepository.TaskInsertionException)
            sut.save(anyTitle())

            runCurrent()

            Assert.assertEquals(
                listOf(
                    CreateTaskUIState(),
                    CreateTaskUIState(loading = true),
                    CreateTaskUIState(error = CreateTaskUIState.Error.Insertion)
                ),
                states
            )
        }
    }

    @Test
    fun save_emitsEmptyTitleErrorOnEmptyTitleValidationError() = runTest {
        val (sut, repository) = makeSUT()

        test(sut.state) { states ->

            repository
                .completeWithError(TaskRepository.TaskValidationException(TaskValidationResult.EMPTY_TITLE))
            sut.save(anyTitle())

            runCurrent()

            Assert.assertEquals(
                listOf(
                    CreateTaskUIState(),
                    CreateTaskUIState(loading = true),
                    CreateTaskUIState(error = CreateTaskUIState.Error.EmptyTitle)
                ),
                states
            )
        }
    }

    @Test
    fun save_emitsInvalidTitleErrorOnInvalidTitleValidationError() = runTest {
        val (sut, repository) = makeSUT()

        test(sut.state) { states ->

            repository
                .completeWithError(TaskRepository.TaskValidationException(TaskValidationResult.INVALID_TITLE))
            sut.save(anyTitle())

            runCurrent()

            Assert.assertEquals(
                listOf(
                    CreateTaskUIState(),
                    CreateTaskUIState(loading = true),
                    CreateTaskUIState(error = CreateTaskUIState.Error.InvalidTitle)
                ),
                states
            )
        }
    }

    @Test
    fun save_emitsUnknownErrorOnCaughtError() = runTest {
        val (sut, repository) = makeSUT()

        test(sut.state) { states ->

            repository.throwError()
            sut.save(anyTitle())

            runCurrent()

            Assert.assertEquals(
                listOf(
                    CreateTaskUIState(),
                    CreateTaskUIState(loading = true),
                    CreateTaskUIState(error = CreateTaskUIState.Error.Unknown)
                ),
                states
            )
        }
    }

    @Test
    fun save_clearsErrorWhenSavingCorrectlyAfterError() = runTest {
        val (sut, repository) = makeSUT()

        test(sut.state) { states ->

            repository.throwError()
            sut.save(anyTitle())

            repository.completeSuccessfully()
            sut.save(anyTitle())

            runCurrent()

            Assert.assertEquals(
                listOf(
                    CreateTaskUIState(),
                    CreateTaskUIState(loading = true),
                    CreateTaskUIState(error = CreateTaskUIState.Error.Unknown),
                    CreateTaskUIState(loading = true),
                    CreateTaskUIState(isTaskSaved = true)
                ),
                states
            )
        }
    }

    // region Helpers

    private fun makeSUT(): Pair<CreateTaskViewModel, RepositoryStub> {
        val repository = RepositoryStub()
        val sut = CreateTaskViewModel(
            saveTask = { repository.save() }
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