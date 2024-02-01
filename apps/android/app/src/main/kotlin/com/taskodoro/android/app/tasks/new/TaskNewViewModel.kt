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

import androidx.annotation.StringRes
import com.taskodoro.android.R
import com.taskodoro.tasks.feature.new.TaskNewUseCase
import com.taskodoro.tasks.validator.TaskValidatorError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import moe.tlaster.precompose.stateholder.SavedStateHolder
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class TaskNewViewModel(
    private val taskNew: TaskNewUseCase,
    savedStateHolder: SavedStateHolder,
) : ViewModel() {

    companion object {
        private const val STATE_KEY = "TASK_NEW_UI_STATE_KEY"
    }

    private val savedState =
        savedStateHolder.consumeRestored(STATE_KEY) as? TaskNewUiState ?: TaskNewUiState()

    private val _uiState = MutableStateFlow(savedState)
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHolder.registerProvider(STATE_KEY) { _uiState.value }
    }

    fun onTitleChanged(title: String) {
        _uiState.update { it.copy(title = title, submitEnabled = title.isNotBlank()) }
    }

    fun onDescriptionChanged(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onDueDateChanged(dueDate: Long) {
        _uiState.update { it.copy(dueDate = dueDate) }
    }

    fun onSubmitClicked() {
        viewModelScope.launch {
            runCatching {
                updateWith(loading = true)
                taskNew(_uiState.value)
                    .onSuccess { updateWith(isTaskSaved = true) }
                    .onFailure { handleError(it as TaskValidatorError) }
            }.onFailure { updateWithError() }
        }
    }

    private suspend fun taskNew(state: TaskNewUiState): Result<Unit> {
        val dueDate = state.dueDate?.let {
            Instant.fromEpochSeconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
        }
        return taskNew(state.title, state.description, dueDate)
    }

    private fun updateWith(
        loading: Boolean = false,
        isTaskSaved: Boolean = false,
    ) {
        _uiState.update { it.copy(loading = loading, isNewTask = isTaskSaved, error = null) }
    }

    fun onErrorShown() {
        _uiState.update { it.copy(error = null) }
    }

    private fun handleError(error: TaskValidatorError) {
        when (error) {
            TaskValidatorError.InvalidTitle ->
                updateWithError(R.string.new_task_invalid_title_error)

            TaskValidatorError.InvalidDueDate ->
                updateWithError(R.string.new_task_invalid_date_error)
        }
    }

    private fun updateWithError(@StringRes error: Int = R.string.new_task_unknown_error) {
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
