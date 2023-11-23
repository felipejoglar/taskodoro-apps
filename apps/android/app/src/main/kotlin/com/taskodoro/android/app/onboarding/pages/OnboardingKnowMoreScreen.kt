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

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.AppTemplate
import com.taskodoro.android.app.ui.components.icons.Icons
import com.taskodoro.android.app.ui.components.icons.Repeat
import com.taskodoro.android.app.ui.components.preview.DynamicColorsPreviews
import com.taskodoro.android.app.ui.components.preview.ScreenPreviews

@Composable
fun OnboardingKnowMoreScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_how_it_works_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            DescriptionText(
                description = R.string.onboarding_how_it_works_description,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 32.dp),
            )

            KnowMoreSteps()

            LastSteps()
        }
    }
}

private val steps = listOf(
    R.string.onboarding_how_it_works_step_one_title to
        R.string.onboarding_how_it_works_step_one_description,
    R.string.onboarding_how_it_works_step_two_title to
        R.string.onboarding_how_it_works_step_two_description,
    R.string.onboarding_how_it_works_step_three_title to
        R.string.onboarding_how_it_works_step_three_description,
    R.string.onboarding_how_it_works_step_four_title to
        R.string.onboarding_how_it_works_step_four_description,
)

@Composable
private fun KnowMoreSteps(
    modifier: Modifier = Modifier,
) {
    steps.forEachIndexed { index, (title, description) ->
        Row(
            modifier = modifier
                .padding(bottom = 16.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Text(
                    text = (index + 1).toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif,
                )
            }

            Column(
                modifier = Modifier.padding(start = 16.dp),
            ) {
                TitleText(title = title)
                DescriptionText(description = description)
            }
        }
    }
}

@Composable
private fun LastSteps(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 48.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Repeat,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(16.dp),
            )
            DescriptionText(
                description = R.string.onboarding_how_it_works_repeat,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 4.dp),
            )
        }

        Column(
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            TitleText(title = R.string.onboarding_how_it_works_last_step_title)
            DescriptionText(description = R.string.onboarding_how_it_works_last_step_description)
        }
    }
}

@Composable
private fun TitleText(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
}

@Composable
private fun DescriptionText(
    @StringRes description: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Text(
        text = stringResource(id = description),
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        modifier = modifier,
    )
}

@ScreenPreviews
@Composable
private fun OnboardingGoodByePreview() {
    AppTemplate {
        Surface {
            OnboardingKnowMoreScreen()
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
            OnboardingKnowMoreScreen()
        }
    }
}
