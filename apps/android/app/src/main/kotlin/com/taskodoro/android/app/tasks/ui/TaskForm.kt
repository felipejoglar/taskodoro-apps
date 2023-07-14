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

package com.taskodoro.android.app.tasks.ui

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.TaskodoroTemplate
import com.taskodoro.android.app.ui.components.buttons.SegmentedButton
import com.taskodoro.android.app.ui.components.buttons.TaskodoroButton
import com.taskodoro.android.app.ui.theme.TaskodoroTheme

/**
 * Reusable Task creation/edition form composable.
 */
@Composable
fun TaskForm(
    title: String,
    description: String,
    priority: Int,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onPriorityChanged: (Int) -> Unit,
    @StringRes submitLabel: Int,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    @StringRes titleErrorLabel: Int? = null,
    @StringRes errorLabel: Int? = null,
) {
    Column(modifier = modifier) {
        TitleTextField(
            title = title,
            onTitleChanged = onTitleChanged,
            titleErrorLabel = titleErrorLabel,
        )

        Spacer(modifier = Modifier.height(8.dp))

        DescriptionTextField(
            description = description,
            onDescriptionChanged = onDescriptionChanged,
        )

        Text(
            text = stringResource(id = R.string.task_form_priority),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.paddingFromBaseline(top = 32.dp, bottom = 8.dp),
        )

        SegmentedButton(
            items = getPriorityLabels(),
            selectedItemIndex = priority,
            onSelectedItemChange = onPriorityChanged,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.weight(1f))

        ErrorLabel(errorLabel)

        TaskodoroButton(
            onClick = onSubmitClicked,
            loading = loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Text(stringResource(id = submitLabel))
        }
    }
}

@Composable
private fun TitleTextField(
    title: String,
    onTitleChanged: (String) -> Unit,
    titleErrorLabel: Int?,
) {
    val titleLabel = stringResource(id = R.string.task_form_title)
    val isTitleError = titleErrorLabel != null

    OutlinedTextField(
        value = title,
        onValueChange = onTitleChanged,
        label = { Text(titleLabel) },
        placeholder = { Text(titleLabel) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
        ),
        isError = isTitleError,
        supportingText = {
            AnimatedVisibility(isTitleError) {
                val label = titleErrorLabel?.let { stringResource(it) } ?: ""
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun DescriptionTextField(
    description: String,
    onDescriptionChanged: (String) -> Unit,
) {
    val descriptionLabel = stringResource(id = R.string.task_form_description)
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChanged,
        label = { Text(descriptionLabel) },
        placeholder = { Text(descriptionLabel) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
    )
}

@Composable
private fun ErrorLabel(errorLabel: Int?) {
    AnimatedVisibility(errorLabel != null) {
        val label = errorLabel?.let { stringResource(it) } ?: ""
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onErrorContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(4.dp),
                )
                .padding(12.dp),
        )
    }
}

@Composable
private fun getPriorityLabels(): List<String> = listOf(
    stringResource(id = R.string.task_form_priority_low),
    stringResource(id = R.string.task_form_priority_medium),
    stringResource(id = R.string.task_form_priority_high),
)

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
            priority = 1,
            onPriorityChanged = {},
            submitLabel = R.string.create_new_task_create_task_button,
            onSubmitClicked = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }
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
private fun TaskFormWithErrorsPreview() {
    TaskodoroTemplate {
        TaskForm(
            title = "",
            onTitleChanged = {},
            description = "",
            onDescriptionChanged = {},
            priority = 1,
            onPriorityChanged = {},
            submitLabel = R.string.create_new_task_create_task_button,
            onSubmitClicked = {},
            titleErrorLabel = R.string.create_new_task_empty_title_error,
            errorLabel = R.string.create_new_task_unknown_error,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }
}
