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
import com.taskodoro.storage.preferences.test.TestPreferences
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OnboardingStoreTest {

    @Test
    fun isOnboarded_returnsFalseWhenNotPreviouslySet() {
        val sut = makeSut()

        assertFalse(sut.isOnboarded().getOrThrow())
    }

    @Test
    fun setOnboarded_setsValueCorrectly() {
        val sut = makeSut()

        sut.setOnboarded()

        assertTrue(sut.isOnboarded().getOrThrow())
    }

    // region Helpers

    private val testPreferences = TestPreferences()

    private fun makeSut(): OnboardingStore {
        return PreferencesOnboardingStore(testPreferences)
    }

    @AfterTest
    fun clearPreferences() {
        testPreferences.clear()
    }

    // endregion
}
