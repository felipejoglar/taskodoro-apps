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

package com.taskodoro.tasks.model

import com.taskodoro.model.Uuid

data class Task(
    val id: Uuid = Uuid(),
    val title: String,
    val description: String? = null,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: Long,
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long = 0,
) {

    enum class Priority {
        LOW, MEDIUM, HIGH;

        companion object {
            fun fromValue(value: Int) =
                when {
                    value < 1 -> LOW
                    value == 1 -> MEDIUM
                    else -> HIGH
                }
        }
    }
}
