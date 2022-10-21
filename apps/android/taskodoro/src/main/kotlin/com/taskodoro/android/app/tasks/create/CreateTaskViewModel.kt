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

import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.util.UUID

class CreateTaskViewModel(
    private val saveTask: (Task) -> Flow<Result<Unit>>,
    private val scope: CoroutineScope,
) {

    private val _state = MutableStateFlow(CreateTaskUIState())
    internal val state = _state.asStateFlow()

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
            .launchIn(scope)
    }

    private fun handleResult(result: Result<Unit>) {
        result
            .onSuccess { updateWith(isTaskSaved = true) }
            .onFailure { handleError(it as? TaskRepository.TaskException) }
    }

    private fun handleError(error: TaskRepository.TaskException?) {
        when (error) {
            TaskRepository.TaskException.EmptyTitle -> updateWithError(CreateTaskUIState.Error.EmptyTitle)
            TaskRepository.TaskException.InvalidTitle -> updateWithError(CreateTaskUIState.Error.InvalidTitle)
            TaskRepository.TaskException.SaveFailed, null -> updateWithError(CreateTaskUIState.Error.Unknown)
        }
    }

    private fun updateWith(
        loading: Boolean = false,
        isTaskSaved: Boolean = false,
        error: CreateTaskUIState.Error? = null
    ) {
        _state.update { it.copy(loading = loading, isTaskSaved = isTaskSaved, error = error) }
    }

    private fun updateWithError(error: CreateTaskUIState.Error) {
        _state.update { it.copy(loading = false, error = error) }
    }
}