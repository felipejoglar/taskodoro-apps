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

package com.taskodoro.android.app.di.tasks.create

import com.taskodoro.tasks.TaskRepository
import com.taskodoro.tasks.create.TaskCreate
import com.taskodoro.tasks.create.TaskCreateUseCase
import com.taskodoro.tasks.model.Task
import com.taskodoro.tasks.validator.TaskValidatorFactory
import com.taskodoro.tasks.validator.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import java.time.Instant
import java.time.ZoneId


@Module
@InstallIn(ViewModelComponent::class)
object TaskCreateDomainModule {

    @Provides
    fun provideTaskValidator(): Validator<Task> {
        return TaskValidatorFactory.create()
    }

    @Provides
    fun provideCurrentTimestamp(): () -> Long {
        return { Instant.now().atZone(ZoneId.of("UTC")).toEpochSecond() }
    }

    @Provides
    @ViewModelScoped
    fun provideTaskCreate(
        repository: TaskRepository,
        validator: Validator<Task>,
        now: () -> Long,
    ): TaskCreateUseCase {
        return TaskCreate(repository, validator, now)
    }
}