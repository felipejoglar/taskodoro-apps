/*
 *    Copyright 2024 Felipe Joglar
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

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqlDelight)
}

kotlin {

    androidTarget()

    iosArm64()
    iosSimulatorArm64()

    sourceSets {

        androidMain.dependencies {
            implementation(libs.sqlDelight.android.driver)
            implementation(libs.sqlDelight.jvm.driver)
        }

        nativeMain.dependencies {
            implementation(libs.sqlDelight.native.driver)
        }
    }

    sourceSets.all {
        languageSettings.enableLanguageFeature("ExpectActualClasses")
    }
}

android {
    namespace = "com.taskodoro"
    compileSdk = config.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = config.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("TaskodoroDB") {
            packageName.set("com.taskodoro.storage.db")
        }
    }
}
