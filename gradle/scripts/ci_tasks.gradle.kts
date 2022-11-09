/*
 *    Copyright 2022 Felipe Joglar
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


tasks.register("androidBuild") {
    group = "build"
    description = "Assemble main outputs for all android modules and variants."

    dependsOn("apps:android:app:clean")
    dependsOn("apps:android:app:assemble")
}

tasks.register("androidTest") {
    group = "verification"
    description = "Run unit tests for all android modules and variants."

    dependsOn("apps:android:app:clean")
    dependsOn("apps:android:app:test")
}

tasks.register("androidUiTest") {
    group = "verification"
    description = "Installs and runs instrumentation tests for all android modules and flavors on connected devices."

    dependsOn("apps:android:app:clean")
    dependsOn("apps:android:app:connectedAndroidTest")
}

tasks.register("multiplatformBuild") {
    group = "build"
    description = "Assembles the outputs of all multiplatform modules."

    dependsOn("taskodoro-core:clean")
    dependsOn("taskodoro-core:assemble")
}

tasks.register("multiplatformTest") {
    group = "verification"
    description = "Runs the tests for all targets in all multiplatform modules."

    dependsOn("taskodoro-core:clean")
    dependsOn("taskodoro-core:allTestsWithAndroid")
}