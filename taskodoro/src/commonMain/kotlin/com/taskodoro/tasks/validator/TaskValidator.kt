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

package com.taskodoro.tasks.validator

import com.taskodoro.tasks.model.Task

internal class TaskValidator(
    private val validators: List<Validator<Task>>,
) : Validator<Task> {

    override fun validate(value: Task) {
        validators.forEach { validator ->
            validator.validate(value)
        }
    }
}

sealed class TaskValidatorError : ValidatorError() {
    data object InvalidTitle : TaskValidatorError()
    data object InvalidDueDate : TaskValidatorError()
}
