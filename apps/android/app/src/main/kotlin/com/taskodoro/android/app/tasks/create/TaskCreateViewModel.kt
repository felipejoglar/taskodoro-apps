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

import androidx.annotation.StringRes
import com.taskodoro.android.R
import com.taskodoro.tasks.create.TaskCreateUseCase
import com.taskodoro.tasks.validator.TaskValidatorError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class TaskCreateViewModel(
    private val taskCreate: TaskCreateUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskCreateUIState())
    val uiState = _uiState.asStateFlow()

    fun onTitleChanged(title: String) {
        _uiState.update { it.copy(title = title, submitEnabled = title.isNotBlank()) }
    }

    fun onDescriptionChanged(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onDueDateChanged(dueDate: Long) {
        _uiState.update { it.copy(dueDate = dueDate) }
    }

    fun onTaskCreateClicked() {
        taskCreate(_uiState.value)
            .flowOn(dispatcher)
            .onStart { updateWith(loading = true) }
            .onSuccess { updateWith(isTaskSaved = true) }
            .onFailure { handleError(it as TaskValidatorError) }
            .catch { updateWithError() }
            .launchIn(viewModelScope)
    }

    private fun taskCreate(state: TaskCreateUIState) =
        flow {
            emit(taskCreate(state.title, state.description, state.dueDate))
        }

    private fun updateWith(
        loading: Boolean = false,
        isTaskSaved: Boolean = false,
    ) {
        _uiState.update { it.copy(loading = loading, isTaskCreated = isTaskSaved, error = null) }
    }

    fun onErrorShown() {
        _uiState.update { it.copy(error = null) }
    }

    private fun handleError(error: TaskValidatorError) {
        when (error) {
            TaskValidatorError.InvalidTitle ->
                updateWithError(R.string.create_new_task_invalid_title_error)

            TaskValidatorError.InvalidDueDate ->
                updateWithError(R.string.create_new_task_invalid_date_error)
        }
    }

    private fun updateWithError(@StringRes error: Int = R.string.create_new_task_unknown_error) {
        _uiState.update { it.copy(loading = false, error = error) }
    }
}

private fun Flow<Result<Unit>>.onSuccess(
    action: () -> Unit,
): Flow<Result<Unit>> =
    onEach { it.onSuccess { action() } }

private fun Flow<Result<Unit>>.onFailure(
    action: (Throwable) -> Unit,
): Flow<Result<Unit>> =
    onEach { it.onFailure { error -> action(error) } }
