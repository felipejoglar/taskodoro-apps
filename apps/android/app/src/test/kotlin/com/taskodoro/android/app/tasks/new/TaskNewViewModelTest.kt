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

package com.taskodoro.android.app.tasks.new

import com.taskodoro.android.R
import com.taskodoro.android.app.helpers.expectEquals
import com.taskodoro.tasks.feature.new.TaskNewUseCase
import com.taskodoro.tasks.validator.TaskValidatorError
import com.taskodoro.tasks.validator.ValidatorError
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import moe.tlaster.precompose.stateholder.SavedStateHolder
import org.junit.Assert
import org.junit.Test

class TaskNewViewModelTest {

    @Test
    fun init_doesNotModifyInitialState() {
        val (sut, _) = makeSUT()

        Assert.assertEquals(TaskNewUiState(), sut.uiState.value)
    }

    @Test
    fun onTitleChanged_updatesTitleAndSendEnabledState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            TaskNewUiState(),
            TaskNewUiState(title = "Hello", submitEnabled = true),
            TaskNewUiState(title = "", submitEnabled = false),
            TaskNewUiState(title = "   ", submitEnabled = false),
        )

        expectEquals(
            flow = sut.uiState,
            expectedValues = expectedStates,
            actions = listOf({
                sut.onTitleChanged("Hello")
            }, {
                sut.onTitleChanged("")
            }, {
                sut.onTitleChanged("   ")
            }),
        )
    }

    @Test
    fun onDescriptionChanged_updatesDescriptionState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            TaskNewUiState(),
            TaskNewUiState(description = "Hello"),
            TaskNewUiState(description = "Hello, World!"),
        )

        expectEquals(
            flow = sut.uiState,
            expectedValues = expectedStates,
            actions = listOf({
                sut.onDescriptionChanged("Hello")
            }, {
                sut.onDescriptionChanged("Hello, World!")
            }),
        )
    }

    @Test
    fun onDueDateChanged_updatesDueDateState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            TaskNewUiState(),
            TaskNewUiState(dueDate = 10),
            TaskNewUiState(dueDate = 20),
            TaskNewUiState(dueDate = 30),
        )

        expectEquals(
            flow = sut.uiState,
            expectedValues = expectedStates,
            actions = listOf({
                sut.onDueDateChanged(10)
            }, {
                sut.onDueDateChanged(20)
            }, {
                sut.onDueDateChanged(30)
            }),
        )
    }

    @Test
    fun onSubmitClicked_emitsCorrectStatesOnSuccessfulSave() {
        val (sut, taskNew) = makeSUT()
        val expectedStates = listOf(
            TaskNewUiState(),
            TaskNewUiState(loading = true),
            TaskNewUiState(isNewTask = true),
        )

        expectEquals(
            flow = sut.uiState,
            expectedValues = expectedStates,
            actions = listOf {
                taskNew.completeSuccessfully()
                sut.onSubmitClicked()
            },
        )
    }

    @Test
    fun onSubmitClicked_emitsInvalidTitleErrorOnInvalidTitleValidationError() {
        val (sut, taskNew) = makeSUT()
        val expectedStates = listOf(
            TaskNewUiState(),
            TaskNewUiState(loading = true),
            TaskNewUiState(error = R.string.new_task_invalid_title_error),
        )

        expectEquals(
            flow = sut.uiState,
            expectedValues = expectedStates,
            actions = listOf {
                val validatorErrors = listOf(TaskValidatorError.InvalidTitle)
                taskNew.completeWithValidatorErrors(validatorErrors)
                sut.onSubmitClicked()
            },
        )
    }

    @Test
    fun onSubmitClicked_emitsUnknownErrorOnCaughtError() {
        val (sut, taskNew) = makeSUT()
        val expectedStates = listOf(
            TaskNewUiState(),
            TaskNewUiState(loading = true),
            TaskNewUiState(error = R.string.new_task_unknown_error),
        )

        expectEquals(
            flow = sut.uiState,
            expectedValues = expectedStates,
            actions = listOf {
                taskNew.throwError()
                sut.onSubmitClicked()
            },
        )
    }

    @Test
    fun onSubmitClicked_clearsErrorWhenSavingCorrectlyAfterError() {
        val (sut, taskNew) = makeSUT()
        val expectedStatesForUnknownError = listOf(
            TaskNewUiState(),
            TaskNewUiState(loading = true),
            TaskNewUiState(error = R.string.new_task_unknown_error),
            TaskNewUiState(loading = true),
            TaskNewUiState(error = R.string.new_task_invalid_date_error),
            TaskNewUiState(loading = true),
            TaskNewUiState(isNewTask = true),
        )

        expectEquals(
            flow = sut.uiState,
            expectedValues = expectedStatesForUnknownError,
            actions = listOf({
                taskNew.throwError()
                sut.onSubmitClicked()
            }, {
                val validatorErrors = listOf(TaskValidatorError.InvalidDueDate)
                taskNew.completeWithValidatorErrors(validatorErrors)
                sut.onSubmitClicked()
            }, {
                taskNew.completeSuccessfully()
                sut.onSubmitClicked()
            }),
        )
    }

    @Test
    fun onErrorShown_clearsError() {
        val (sut, taskNew) = makeSUT()
        val expectedStates = listOf(
            TaskNewUiState(),
            TaskNewUiState(loading = true),
            TaskNewUiState(error = R.string.new_task_unknown_error),
            TaskNewUiState(),
        )

        expectEquals(
            flow = sut.uiState,
            expectedValues = expectedStates,
            actions = listOf({
                taskNew.throwError()
                sut.onSubmitClicked()
            }, {
                sut.onErrorShown()
            }),
        )
    }

    // region Helpers

    private fun makeSUT(): Pair<TaskNewViewModel, TaskNewUseCaseStub> {
        val taskNew = TaskNewUseCaseStub()
        val sut = TaskNewViewModel(
            taskNew = taskNew,
            savedStateHolder = SavedStateHolder("", null),
        )

        return sut to taskNew
    }

    private class TaskNewUseCaseStub : TaskNewUseCase {
        private var result: Result<Unit>? = null

        override suspend fun invoke(
            title: String,
            description: String?,
            dueDate: Long?,
        ): Result<Unit> {
            return result!!
        }

        fun completeSuccessfully() {
            result = Result.success(Unit)
        }

        fun completeWithValidatorErrors(validatorErrors: List<ValidatorError>) {
            result = Result.failure(validatorErrors.first())
        }

        fun throwError() {
            result = null
        }
    }

    // endregion
}
