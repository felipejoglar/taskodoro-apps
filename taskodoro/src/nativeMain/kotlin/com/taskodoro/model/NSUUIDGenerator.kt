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

import platform.Foundation.NSUUID

class NSUUIDGenerator : UuidGenerator {
    override fun randomUuidString() = NSUUID().UUIDString()

    override fun isValidUuidValue(value: String) = try {
        NSUUID(value)
        true
    } catch (exception: Exception) {
        false
    }
}
