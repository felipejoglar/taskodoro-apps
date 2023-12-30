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

package com.taskodoro.storage.preferences.test

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import com.taskodoro.storage.preferences.Preferences
import com.taskodoro.storage.preferences.datastore.DataStorePreferences
import kotlinx.coroutines.runBlocking
import okio.Path.Companion.toPath

private lateinit var dataStore: DataStore<androidx.datastore.preferences.core.Preferences>

class TestPreferences(
    path: String = "test.preferences_pb",
) : Preferences {

    private val dataStorePreferences by lazy { DataStorePreferences(dataStore) }

    init {
        if (::dataStore.isInitialized.not()) {
            dataStore = PreferenceDataStoreFactory.createWithPath(produceFile = { path.toPath() })
        }
    }

    override fun getBoolean(key: String): Boolean {
        return dataStorePreferences.getBoolean(key)
    }

    override fun setBoolean(key: String, value: Boolean) {
        dataStorePreferences.setBoolean(key, value)
    }

    fun clear(): Unit = runBlocking {
        dataStore.edit { it.clear() }
    }
}
