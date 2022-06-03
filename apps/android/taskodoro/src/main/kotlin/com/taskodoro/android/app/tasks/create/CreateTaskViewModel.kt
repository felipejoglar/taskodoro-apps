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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.model.Task
import com.taskodoro.tasks.model.TaskValidation
import java.time.Instant
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class CreateTaskViewModel(
    private val saveTask: (Task) -> Flow<Result<Unit>>,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskUIState.INITIAL)
    internal val uiState = _uiState.asStateFlow()

    fun save(title: String) {
        val task = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            createdAt = Instant.now().epochSecond
        )

        saveTask(task)
            .onStart { updateWith(loading = true) }
            .onEach(::handleResult)
            .catch { updateWithError(CreateTaskUIState.Error.Unknown) }
            .launchIn(viewModelScope)
    }

    private fun handleResult(result: Result<Unit>) {
        result
            .onSuccess { updateWith(isTaskSaved = true) }
            .onFailure(::handleError)
    }

    private fun handleError(error: Throwable) {
        when (error) {
            is TaskRepository.TaskInsertionException -> updateWithError(CreateTaskUIState.Error.Insertion)
            is TaskRepository.TaskValidationException -> handleValidationError(error)
        }
    }

    private fun handleValidationError(error: TaskRepository.TaskValidationException) {
        when (error.validationResult) {
            TaskValidation.EMPTY_TITLE -> updateWithError(CreateTaskUIState.Error.EmptyTitle)
            TaskValidation.INVALID_TITLE -> updateWithError(CreateTaskUIState.Error.InvalidTitle)
            else -> {}
        }
    }

    private fun updateWith(loading: Boolean = false, isTaskSaved: Boolean = false) {
        _uiState.update { it.copy(loading = loading, isTaskSaved = isTaskSaved) }
    }

    private fun updateWithError(error: CreateTaskUIState.Error) {
        _uiState.update { it.copy(loading = false, error = error) }
    }
}