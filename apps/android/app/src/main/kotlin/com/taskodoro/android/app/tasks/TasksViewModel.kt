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

package com.taskodoro.android.app.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskodoro.tasks.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TasksViewModel(
    private val getTasks: Flow<List<Task>>,
) : ViewModel() {

    private val _tasks = MutableStateFlow(emptyList<Task>())
    val tasks = _tasks.asStateFlow()

    fun getTasks() {
        getTasks
            .onEach(::updateState)
            .catch { updateState(emptyList()) }
            .launchIn(viewModelScope)
    }

    private fun updateState(tasks: List<Task>) {
        _tasks.update { tasks }
    }
}
