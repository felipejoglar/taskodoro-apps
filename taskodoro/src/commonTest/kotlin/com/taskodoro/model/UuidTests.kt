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

package com.taskodoro.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class UuidTests {

    @Test
    fun fromValue_returnsUuidOnValidValue() {
        val uuidString = validUUIDString

        val uuid = Uuid.from(uuidString)

        assertEquals(uuidString, uuid?.uuidString)
    }

    @Test
    fun fromValue_returnsNullOnInvalidValue() {
        val uuidString = "invalid UUID"

        val uuid = Uuid.from(uuidString)

        assertNull(uuid)
    }

    @Test
    fun toString_returnsFalseWhenComparingNonUuidType() {
        val uuid = Uuid.from(validUUIDString)!!

        assertEquals(validUUIDString, uuid.toString())
    }

    @Test
    fun equals_returnsFalseWhenComparingNonUuidType() {
        val uuid = Uuid.from(validUUIDString)!!

        assertFalse(uuid.equals("null"))
    }

    @Test
    @Suppress("SENSELESS_COMPARISON")
    fun equals_returnsFalseWhenComparingNull() {
        val uuid = Uuid.from(validUUIDString)!!

        assertFalse(uuid == null)
    }

    @Test
    fun equals_returnsFalseOnNonMatchingUuidStringValues() {
        val uuid = Uuid.from(validUUIDString)!!
        val otherUUID = Uuid.from(anotherValidUUIDString)!!

        assertNotEquals(uuid, otherUUID)
    }

    @Test
    fun equals_returnsTrueOnMatchingUuidStringValues() {
        val uuid = Uuid.from(validUUIDString)!!
        val otherUUID = Uuid.from(validUUIDString)!!

        assertEquals(uuid, otherUUID)
    }

    // region Helpers

    private val validUUIDString = "66F5A4F9-53F2-42F9-8C9F-69EFE5F1F1E3"
    private val anotherValidUUIDString = "34977d81-936d-4305-bcc6-9640cfaef197"

    // endregion
}
