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

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "UUID")
class Uuid {
    var uuidString: String = uuidGenerator.randomUuidString().uppercase()
        private set

    companion object {
        private val uuidGenerator = UuidGeneratorFactory.create()

        fun from(uuidString: String): Uuid? {
            if (uuidGenerator.isValidUuidValue(uuidString)) {
                return Uuid().apply { this.uuidString = uuidString.uppercase() }
            }

            return null
        }
    }

    override fun toString(): String = uuidString

    override fun equals(other: Any?): Boolean {
        if (other !is Uuid) return false

        return this.uuidString.uppercase() == other.uuidString.uppercase()
    }

    override fun hashCode(): Int {
        return uuidString.hashCode()
    }
}

internal interface UuidGenerator {
    fun randomUuidString(): String
    fun isValidUuidValue(value: String): Boolean
}

internal expect object UuidGeneratorFactory {
    fun create(): UuidGenerator
}
