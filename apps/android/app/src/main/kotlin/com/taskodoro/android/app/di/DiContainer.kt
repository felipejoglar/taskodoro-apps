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

package com.taskodoro.android.app.di

import android.content.Context
import com.taskodoro.onboarding.OnboardingStoreFactory
import com.taskodoro.storage.tasks.LocalTaskRepository
import com.taskodoro.storage.tasks.TaskStoreFactory
import com.taskodoro.tasks.new.TaskNew
import com.taskodoro.tasks.new.TaskNewUseCase
import com.taskodoro.tasks.validator.TaskValidatorFactory
import java.time.Instant
import java.time.ZoneId

class DiContainer(
    private val applicationContext: Context,
) {

    val onboardingStore by lazy { OnboardingStoreFactory(applicationContext).create() }

    val taskNewUseCase: TaskNewUseCase
        get() {
            val validator = TaskValidatorFactory.create()

            return TaskNew(
                repository = taskRepository,
                validator = validator,
                now = { Instant.now().atZone(ZoneId.of("UTC")).toEpochSecond() },
            )
        }

    private val taskRepository by lazy {
        LocalTaskRepository(TaskStoreFactory(applicationContext).create())
    }
}
