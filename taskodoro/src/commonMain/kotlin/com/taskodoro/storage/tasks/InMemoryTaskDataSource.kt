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

package com.taskodoro.storage.tasks

import com.taskodoro.tasks.data.TaskLocalDataSource
import com.taskodoro.tasks.model.Task

class InMemoryTaskDataSource : TaskLocalDataSource {

    companion object {
        private const val TASK_COUNT = 20
    }
    override fun getAllTasks(): List<Task> =
        List(TASK_COUNT) {
            Task(
                title = "Task $it title",
                dueDate = it.toLong(),
                createdAt = it.toLong(),
            )
        }
}
