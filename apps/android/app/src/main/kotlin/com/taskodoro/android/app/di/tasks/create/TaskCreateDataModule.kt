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

import android.content.Context
import com.taskodoro.storage.tasks.LocalTaskRepository
import com.taskodoro.storage.tasks.TaskStore
import com.taskodoro.storage.tasks.TaskStoreFactory
import com.taskodoro.tasks.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskCreateDataModule {

    @Provides
    @Singleton
    fun provideTaskStore(
        @ApplicationContext applicationContext: Context,
    ): TaskStore {
        return TaskStoreFactory(applicationContext).create()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        store: TaskStore,
    ): TaskRepository {
        return LocalTaskRepository(store)
    }
}
