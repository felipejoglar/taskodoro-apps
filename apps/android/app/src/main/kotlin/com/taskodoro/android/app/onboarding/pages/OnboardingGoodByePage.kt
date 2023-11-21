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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.buttons.Button
import com.taskodoro.android.app.ui.components.buttons.TextButton
import com.taskodoro.android.app.ui.components.icons.History
import com.taskodoro.android.app.ui.components.icons.Icons
import com.taskodoro.android.app.ui.components.preview.DynamicColorsPreviews
import com.taskodoro.android.app.ui.components.preview.ScreenPreviews
import com.taskodoro.android.app.ui.theme.AppTheme

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
        OnboardingPage(
            title = title,
            description = description,
            icon = icon,
            background = background,
        )

        ActionButtons(
            onContinueClicked = onContinueClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun ActionButtons(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 64.dp),
    ) {
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Text(text = stringResource(id = R.string.onboarding_know_more))
        }
        Button(
            onClick = onContinueClicked,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.onboarding_continue))
        }
    }
}

@ScreenPreviews
@Composable
private fun OnboardingGoodByePreview() {
    AppTheme(
        useDynamicColors = false,
    ) {
        Surface {
            Preview()
        }
    }
}

@DynamicColorsPreviews
@Composable
private fun OnboardingGoodByeDynamicColorPreview() {
    AppTheme {
        Surface {
            Preview()
        }
    }
}

@Composable
private fun Preview() {
    OnboardingGoodByePage(
        title = "A section title",
        description = "This is a section description where we explain things",
        icon = Icons.History,
        background = R.drawable.onboarding_background_even,
        onContinueClicked = {},
    )
}
