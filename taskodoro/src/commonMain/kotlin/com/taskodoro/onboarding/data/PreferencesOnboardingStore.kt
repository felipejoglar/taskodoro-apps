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

package com.taskodoro.onboarding.data

import com.taskodoro.onboarding.OnboardingStore
import com.taskodoro.storage.preferences.Preferences

internal class PreferencesOnboardingStore(
    private val preferences: Preferences,
) : OnboardingStore {

    companion object {
        private const val IS_ONBOARDED_KEY = "is_onboarded_key"
    }

    override fun isOnboarded(): Result<Boolean> {
        return runCatching {
            preferences.getBoolean(IS_ONBOARDED_KEY)
        }
    }

    override fun setOnboarded(): Result<Unit> {
        return runCatching {
            preferences.setBoolean(IS_ONBOARDED_KEY, true)
        }
    }
}
