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

import com.taskodoro.android.R
import com.taskodoro.android.app.helpers.expectEquals
import com.taskodoro.tasks.create.CreateTaskUseCase
import com.taskodoro.tasks.validator.TaskValidatorError
import com.taskodoro.tasks.validator.ValidatorError
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    fun onDueDateChanged_updatesDueDateState() {
        val (sut, _) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(dueDate = 10),
            CreateTaskUIState(dueDate = 20),
            CreateTaskUIState(dueDate = 30),
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
    fun onCreateTaskClicked_emitsCorrectStatesOnSuccessfulSave() {
        val (sut, createTask) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(isTaskCreated = true),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                createTask.completeSuccessfully()
                sut.onCreateTaskClicked()
            },
        )
    }

    @Test
    fun onCreateTaskClicked_emitsEmptyTitleErrorOnEmptyTitleValidationError() {
        val (sut, createTask) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(titleError = R.string.create_new_task_empty_title_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                val validatorErrors = listOf(TaskValidatorError.Title.Empty)
                createTask.completeWithValidatorErrors(validatorErrors)
                sut.onCreateTaskClicked()
            },
        )
    }

    @Test
    fun onCreateTaskClicked_emitsInvalidTitleErrorOnInvalidTitleValidationError() {
        val (sut, createTask) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(titleError = R.string.create_new_task_invalid_title_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                val validatorErrors = listOf(TaskValidatorError.Title.Invalid)
                createTask.completeWithValidatorErrors(validatorErrors)
                sut.onCreateTaskClicked()
            },
        )
    }

    @Test
    fun onCreateTaskClicked_emitsUnknownErrorOnCaughtError() {
        val (sut, createTask) = makeSUT()
        val expectedStates = listOf(
            CreateTaskUIState(),
            CreateTaskUIState(loading = true),
            CreateTaskUIState(error = R.string.create_new_task_unknown_error),
        )

        expectEquals(
            flow = sut.state,
            expectedValues = expectedStates,
            actions = listOf {
                createTask.throwError()
                sut.onCreateTaskClicked()
            },
        )
    }

    @Test
    fun onCreateTaskClicked_clearsErrorWhenSavingCorrectlyAfterError() {
        val (sut, createTask) = makeSUT()
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
                createTask.throwError()
                sut.onCreateTaskClicked()
            }, {
                val validatorErrors = listOf(TaskValidatorError.Title.Empty)
                createTask.completeWithValidatorErrors(validatorErrors)
                sut.onCreateTaskClicked()
            }, {
                createTask.completeSuccessfully()
                sut.onCreateTaskClicked()
            }),
        )
    }

    // region Helpers

    private fun makeSUT(): Pair<CreateTaskViewModel, CreateTaskUseCaseStub> {
        val createTask = CreateTaskUseCaseStub()
        val sut = CreateTaskViewModel(
            createTask = createTask,
            dispatcher = UnconfinedTestDispatcher(),
        )

        return sut to createTask
    }

    private class CreateTaskUseCaseStub : CreateTaskUseCase {
        private var result: CreateTaskUseCase.Result? = null

        override fun invoke(
            title: String,
            description: String?,
            dueDate: Long?,
        ): CreateTaskUseCase.Result {
            return result!!
        }

        fun completeSuccessfully() {
            result = CreateTaskUseCase.Result.Success
        }

        fun completeWithValidatorErrors(validatorErrors: List<ValidatorError>) {
            result = CreateTaskUseCase.Result.Failure(validatorErrors)
        }

        fun throwError() {
            result = null
        }
    }

    // endregion
}
