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

package com.taskodoro.android.app.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.taskodoro.android.R
import com.taskodoro.android.app.onboarding.pages.OnboardingGoodByePage
import com.taskodoro.android.app.onboarding.pages.OnboardingPage
import com.taskodoro.android.app.onboarding.pages.OnboardingWelcomePage
import com.taskodoro.android.app.ui.components.icons.CheckCircle
import com.taskodoro.android.app.ui.components.icons.History
import com.taskodoro.android.app.ui.components.icons.Icons
import com.taskodoro.android.app.ui.components.icons.Psychology
import com.taskodoro.android.app.ui.components.pager.Page
import com.taskodoro.android.app.ui.components.pager.Pager
import com.taskodoro.android.app.ui.components.pager.Pages

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
) {
    val pages = Pages(
        items = listOf(
            Page(1U) {
                OnboardingWelcomePage(
                    background = R.drawable.onboarding_background_even,
                )
            },
            Page(2U) {
                OnboardingPage(
                    title = stringResource(id = R.string.onboarding_organize_title),
                    description = stringResource(id = R.string.onboarding_organize_description),
                    icon = Icons.History,
                    background = R.drawable.onboarding_background_odd,
                )
            },
            Page(3U) {
                OnboardingPage(
                    title = stringResource(id = R.string.onboarding_productivity_title),
                    description = stringResource(id = R.string.onboarding_productivity_description),
                    icon = Icons.CheckCircle,
                    background = R.drawable.onboarding_background_even,
                )
            },
            Page(4U) {
                OnboardingGoodByePage(
                    title = stringResource(id = R.string.onboarding_stress_title),
                    description = stringResource(id = R.string.onboarding_stress_description),
                    icon = Icons.Psychology,
                    background = R.drawable.onboarding_background_odd,
                    onContinueClicked = onContinueClicked,
                )
            },
        ),
    )
    Pager(pages = pages)
}
