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

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.sqlDelight).apply(false)

    alias(libs.plugins.spotless).apply(true)
    alias(libs.plugins.detekt).apply(true)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Spotless configuration
configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    val ktlintVersion = "0.48.2"

    kotlin {
        target("**/*.kt")
        targetExclude("$buildDir/**/*.kt")
        targetExclude("bin/**/*.kt")

        ktlint(ktlintVersion)
    }

    kotlinGradle {
        target("*.gradle.kts")

        ktlint(ktlintVersion)
    }
}

allprojects {

    // Detekt configuration
    apply(plugin = "io.gitlab.arturbosch.detekt").also {

        detekt {
            config.from(rootProject.files("scripts/detekt.yml"))
        }

        tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
            setSource(files(project.projectDir))
            exclude("**/build/**")
            exclude {
                it.file.relativeTo(projectDir).startsWith(project.buildDir.relativeTo(projectDir))
            }

            reports {
                sarif.required.set(true)
            }
        }
    }
}
