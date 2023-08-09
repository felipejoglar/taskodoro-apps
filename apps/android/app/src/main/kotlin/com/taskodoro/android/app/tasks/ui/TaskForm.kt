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

package com.taskodoro.android.app.tasks.ui

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.TaskodoroTextField
import com.taskodoro.android.app.ui.theme.TaskodoroTheme

/**
 * Reusable Task creation/edition form composable.
 */
@Composable
fun TaskForm(
    title: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(1f),
        ) {
            TitleTextField(
                title = title,
                onTitleChanged = onTitleChanged,
                modifier = Modifier.padding(top = 4.dp),
            )

            DescriptionTextField(
                description = description,
                onDescriptionChanged = onDescriptionChanged,
            )

            Spacer(modifier = Modifier.weight(1.0f))
        }

        ExtraFieldsRow(scrollState = scrollState)
    }
}

@Composable
private fun TitleTextField(
    title: String,
    onTitleChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val titleLabel = stringResource(id = R.string.task_form_title)

    TaskodoroTextField(
        value = title,
        onValueChanged = onTitleChanged,
        placeHolderText = titleLabel,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        modifier = modifier.focusRequester(focusRequester),
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun DescriptionTextField(
    description: String,
    onDescriptionChanged: (String) -> Unit,
) {
    val descriptionLabel = stringResource(id = R.string.task_form_description)
    TaskodoroTextField(
        value = description,
        onValueChanged = onDescriptionChanged,
        placeHolderText = descriptionLabel,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        modifier = Modifier
            .defaultMinSize(minHeight = 128.dp),
    )
}

private const val GRADIENT_START = 0.0f
private const val GRADIENT_MIDDLE = 0.5f
private const val GRADIENT_END = 1.0f

@Composable
private fun ExtraFieldsRow(
    scrollState: ScrollState,
) {
    Box(
        modifier = Modifier
            .height(IntrinsicSize.Max),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            ExtraFieldChip()
        }

        Spacer(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight()
                .width(16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        GRADIENT_START to MaterialTheme.colorScheme.background,
                        GRADIENT_MIDDLE to MaterialTheme.colorScheme.background,
                        GRADIENT_END to Color.Transparent,
                    ),
                ),
        )

        Spacer(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        GRADIENT_START to Color.Transparent,
                        GRADIENT_MIDDLE to MaterialTheme.colorScheme.background,
                        GRADIENT_END to MaterialTheme.colorScheme.background,
                    ),
                ),
        )

        if (scrollState.canScrollForward) {
            Spacer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            )
        }
    }
}

@Composable
private fun ExtraFieldChip() {
    AssistChip(
        onClick = { },
        label = {
            Text(
                text = "Due date",
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.CalendarMonth,
                contentDescription = "Due Date",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.outline,
            )
        },
        border = AssistChipDefaults.assistChipBorder(
            borderWidth = 0.5.dp,
            borderColor = MaterialTheme.colorScheme.outline,
        ),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .padding(vertical = 4.dp),
    )
}

@Preview(
    name = "Day Mode",
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Night Mode",
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun TaskFormPreview() {
    TaskodoroTheme {
        TaskForm(
            title = "",
            onTitleChanged = {},
            description = "",
            onDescriptionChanged = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        )
    }
}
