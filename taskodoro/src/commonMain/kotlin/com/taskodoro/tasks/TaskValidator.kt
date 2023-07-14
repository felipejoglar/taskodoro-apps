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

package com.taskodoro.tasks

import com.taskodoro.tasks.model.Task
import com.taskodoro.tasks.model.TaskValidationResult

object TaskValidator {
    private const val MINIMUM_TITLE_LENGTH = 4

    fun validate(task: Task): TaskValidationResult =
        if (task.title.isBlank()) {
            TaskValidationResult.EMPTY_TITLE
        } else if (task.title.trim().length < MINIMUM_TITLE_LENGTH) {
            TaskValidationResult.INVALID_TITLE
        } else {
            TaskValidationResult.SUCCESS
        }
}
