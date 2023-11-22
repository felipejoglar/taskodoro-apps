/*
 *    Copyright 2023 Green Peaks Studio
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

package com.taskodoro.android.app.onboarding.pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.AppTemplate
import com.taskodoro.android.app.ui.components.buttons.Button
import com.taskodoro.android.app.ui.components.buttons.TextButton
import com.taskodoro.android.app.ui.components.icons.History
import com.taskodoro.android.app.ui.components.icons.Icons
import com.taskodoro.android.app.ui.components.preview.DynamicColorsPreviews
import com.taskodoro.android.app.ui.components.preview.ScreenPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingGoodByePage(
    title: String,
    description: String,
    icon: ImageVector,
    @DrawableRes background: Int,
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize(),
    ) {
        var openKnowMoreSection by rememberSaveable { mutableStateOf(false) }

        OnboardingPage(
            title = title,
            description = description,
            icon = icon,
            background = background,
        )

        ActionButtons(
            onContinueClicked = onContinueClicked,
            onKnowMoreClicked = { openKnowMoreSection = true },
            modifier = Modifier
                .align(Alignment.BottomCenter),
        )

        if (openKnowMoreSection) {
            val bottomSheetState = rememberModalBottomSheetState()
            val onDismiss = { openKnowMoreSection = false }

            ModalBottomSheet(
                onDismissRequest = onDismiss,
                sheetState = bottomSheetState,
            ) {
                OnboardingKnowMoreScreen(
                    onDismiss = onDismiss,
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun ActionButtons(
    onContinueClicked: () -> Unit,
    onKnowMoreClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 64.dp),
    ) {
        TextButton(
            onClick = onKnowMoreClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_know_more),
                fontWeight = FontWeight.Bold,
            )
        }
        Button(
            onClick = onContinueClicked,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.navigation_continue))
        }
    }
}

@ScreenPreviews
@Composable
private fun OnboardingGoodByePreview() {
    AppTemplate {
        Surface {
            PreviewPage()
        }
    }
}

@DynamicColorsPreviews
@Composable
private fun OnboardingGoodByeDynamicColorPreview() {
    AppTemplate(
        useDynamicColors = true,
    ) {
        Surface {
            PreviewPage()
        }
    }
}

@Composable
private fun PreviewPage() {
    OnboardingGoodByePage(
        title = "A section title",
        description = "This is a section description where we explain things",
        icon = Icons.History,
        background = R.drawable.onboarding_background_even,
        onContinueClicked = {},
    )
}
