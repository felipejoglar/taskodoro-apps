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

package com.taskodoro.android.app.onboarding.pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.Image
import com.taskodoro.android.app.ui.components.preview.DynamicColorsPreviews
import com.taskodoro.android.app.ui.components.preview.ScreenPreviews
import com.taskodoro.android.app.ui.components.vectors.LogoColoured
import com.taskodoro.android.app.ui.components.vectors.Vectors
import com.taskodoro.android.app.ui.theme.AppTheme

@Composable
fun OnboardingWelcomePage(
    @DrawableRes background: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize(),
    ) {
        Image(
            id = background,
            contentDescription = null,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = Vectors.LogoColoured,
                contentDescription = Vectors.LogoColoured.name,
                modifier = Modifier
                    .size(172.dp),
            )
            Text(
                text = buildAnnotatedString {
                    val appName = stringResource(id = R.string.app_name)
                    val welcomeMessage = stringResource(id = R.string.onboarding_welcome, appName)

                    append(welcomeMessage.replace(appName, ""))
                    withStyle(highlightedSpanStyle()) {
                        append(appName)
                    }
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(16.dp),
            )
        }
    }
}

@Composable
private fun highlightedSpanStyle() = SpanStyle(
    brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.tertiary,
        ),
    ),
    fontWeight = FontWeight.Bold,
)

@ScreenPreviews
@Composable
private fun OnboardingWelcomePagePreview() {
    AppTheme(
        useDynamicColors = false,
    ) {
        Surface {
            OnboardingWelcomePage(
                background = R.drawable.onboarding_background_even,
            )
        }
    }
}

@DynamicColorsPreviews
@Composable
private fun OnboardingWelcomePageDynamicColorPreview() {
    AppTheme {
        Surface {
            OnboardingWelcomePage(
                background = R.drawable.onboarding_background_even,
            )
        }
    }
}
