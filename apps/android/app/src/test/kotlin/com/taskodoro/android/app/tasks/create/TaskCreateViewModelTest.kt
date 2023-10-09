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

package com.taskodoro.android.app.tasks.create

import androidx.lifecycle.SavedStateHandle
import com.taskodoro.android.R
import com.taskodoro.android.app.helpers.expectEquals
import com.taskodoro.tasks.create.TaskCreateUseCase
import com.taskodoro.tasks.validator.TaskValidatorError
import com.taskodoro.tasks.validator.ValidatorError
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Test

class TaskCreateViewModelTest {

    @Test
    fun init_doesNotModifyInitialState() {
        val (sut, _) = makeSUT()

        Assert.assertEquals(TaskCreateUIState(), sut.state.value)
    }

    @Test
    fun onTitleChanged_updatesTitleAndSendEnabledState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            TaskCreateUIState(),
            TaskCreateUIState(title = "Hello", submitEnabled = true),
            TaskCreateUIState(title = "", submitEnabled = false),
            TaskCreateUIState(title = "   ", submitEnabled = false),
        )

        expectEquals(
            flow = sut.state,
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
            TaskCreateUIState(),
            TaskCreateUIState(description = "Hello"),
            TaskCreateUIState(description = "Hello, World!"),
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
    fun onDueDateChanged_updatesDueDateState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            TaskCreateUIState(),
            TaskCreateUIState(dueDate = 10),
            TaskCreateUIState(dueDate = 20),
            TaskCreateUIState(dueDate = 30),
        )

        expectEquals(
            flow = sut.state,
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
    fun onTaskCreateClicked_emitsCorrectStatesOnSuccessfulSave() {
        val (sut, taskCreate) = makeSUT()
        val expectedStates = listOf(
            TaskCreateUIState(),
            TaskCreateUIState(loading = true),
            TaskCreateUIState(isTaskCreated = true),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                taskCreate.completeSuccessfully()
                sut.onTaskCreateClicked()
            },
        )
    }

    @Test
    fun onTaskCreateClicked_emitsInvalidTitleErrorOnInvalidTitleValidationError() {
        val (sut, taskCreate) = makeSUT()
        val expectedStates = listOf(
            TaskCreateUIState(),
            TaskCreateUIState(loading = true),
            TaskCreateUIState(error = R.string.create_new_task_invalid_title_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                val validatorErrors = listOf(TaskValidatorError.InvalidTitle)
                taskCreate.completeWithValidatorErrors(validatorErrors)
                sut.onTaskCreateClicked()
            },
        )
    }

    @Test
    fun onTaskCreateClicked_emitsUnknownErrorOnCaughtError() {
        val (sut, taskCreate) = makeSUT()
        val expectedStates = listOf(
            TaskCreateUIState(),
            TaskCreateUIState(loading = true),
            TaskCreateUIState(error = R.string.create_new_task_unknown_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                taskCreate.throwError()
                sut.onTaskCreateClicked()
            },
        )
    }

    @Test
    fun onTaskCreateClicked_clearsErrorWhenSavingCorrectlyAfterError() {
        val (sut, taskCreate) = makeSUT()
        val expectedStatesForUnknownError = listOf(
            TaskCreateUIState(),
            TaskCreateUIState(loading = true),
            TaskCreateUIState(error = R.string.create_new_task_unknown_error),
            TaskCreateUIState(loading = true),
            TaskCreateUIState(error = R.string.create_new_task_invalid_date_error),
            TaskCreateUIState(loading = true),
            TaskCreateUIState(isTaskCreated = true),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStatesForUnknownError,
            actions = listOf({
                taskCreate.throwError()
                sut.onTaskCreateClicked()
            }, {
                val validatorErrors = listOf(TaskValidatorError.InvalidDueDate)
                taskCreate.completeWithValidatorErrors(validatorErrors)
                sut.onTaskCreateClicked()
            }, {
                taskCreate.completeSuccessfully()
                sut.onTaskCreateClicked()
            }),
        )
    }

    @Test
    fun onErrorShown_clearsError() {
        val (sut, taskCreate) = makeSUT()
        val expectedStates = listOf(
            TaskCreateUIState(),
            TaskCreateUIState(loading = true),
            TaskCreateUIState(error = R.string.create_new_task_unknown_error),
            TaskCreateUIState(),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf({
                taskCreate.throwError()
                sut.onTaskCreateClicked()
            }, {
                sut.onErrorShown()
            }),
        )
    }

    // region Helpers

    private fun makeSUT(): Pair<TaskCreateViewModel, TaskCreateUseCaseStub> {
        val taskCreate = TaskCreateUseCaseStub()
        val sut = TaskCreateViewModel(
            taskCreate = taskCreate,
            savedStateHandle = SavedStateHandle(),
            dispatcher = UnconfinedTestDispatcher(),
        )

        return sut to taskCreate
    }

    private class TaskCreateUseCaseStub : TaskCreateUseCase {
        private var result: Result<Unit>? = null

        override fun invoke(
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
