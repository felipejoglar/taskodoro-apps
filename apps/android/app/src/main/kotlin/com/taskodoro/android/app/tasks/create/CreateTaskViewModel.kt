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

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskodoro.android.R
import com.taskodoro.tasks.create.CreateTaskUseCase
import com.taskodoro.tasks.model.Task
import com.taskodoro.tasks.validator.TaskValidatorError
import com.taskodoro.tasks.validator.ValidatorError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import java.time.Instant

class CreateTaskViewModel(
    private val createTask: CreateTaskUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(CreateTaskUIState())
    internal val state = _state.asStateFlow()

    fun onTitleChanged(title: String) {
        _state.update { it.copy(title = title) }
    }

    fun onDescriptionChanged(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun onPriorityChanged(priority: Int) {
        _state.update { it.copy(priority = priority) }
    }

    fun onCreateTaskClicked() {
        createTask()
            .flowOn(dispatcher)
            .onStart { updateWith(loading = true) }
            .onEach(::handleResult)
            .catch { updateWithError(R.string.create_new_task_unknown_error) }
            .launchIn(viewModelScope)
    }

    private fun createTask() = flow { emit(createTask(state.value.title, state.value.description)) }

    private fun handleResult(result: CreateTaskUseCase.Result) {
        result
            .onSuccess { updateWith(isTaskSaved = true) }
            .onFailure { handleErrors(it) }
    }

    private fun handleErrors(errors: List<ValidatorError>) {
        val titleErrors = errors.filterIsInstance<TaskValidatorError.Title>()

        when (titleErrors.first()) {
            TaskValidatorError.Title.Empty ->
                updateWithTitleError(R.string.create_new_task_empty_title_error)

            TaskValidatorError.Title.Invalid ->
                updateWithTitleError(R.string.create_new_task_invalid_title_error)
        }
    }

    private fun updateWith(
        loading: Boolean = false,
        isTaskSaved: Boolean = false,
    ) {
        _state.update {
            it.copy(
                loading = loading,
                isTaskCreated = isTaskSaved,
                titleError = null,
                error = null,
            )
        }
    }

    private fun updateWithError(@StringRes error: Int) {
        _state.update { it.copy(loading = false, error = error) }
    }

    private fun updateWithTitleError(@StringRes titleError: Int) {
        _state.update { it.copy(loading = false, titleError = titleError) }
    }
}

private fun CreateTaskUseCase.Result.onSuccess(action: () -> Unit) = apply {
    if (this is CreateTaskUseCase.Result.Success) action()
}

private fun CreateTaskUseCase.Result.onFailure(action: (List<ValidatorError>) -> Unit) = apply {
    if (this is CreateTaskUseCase.Result.Failure) action(errors)
}
