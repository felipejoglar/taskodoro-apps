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

package com.taskodoro.storage.preferences.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.taskodoro.storage.preferences.Preferences
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.Preferences as DataStorePreferences

internal class DataStorePreferences(
    private val dataStore: DataStore<DataStorePreferences>,
) : Preferences {

    override suspend fun getBoolean(key: String): Boolean {
        return dataStore.data.first()[booleanPreferencesKey(key)] ?: false
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }
}
