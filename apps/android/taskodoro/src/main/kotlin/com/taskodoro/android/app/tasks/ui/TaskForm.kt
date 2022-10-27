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

package com.taskodoro.android.app.tasks.ui

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taskodoro.android.app.R
import com.taskodoro.android.app.ui.components.buttons.SegmentedButton
import com.taskodoro.android.app.ui.theme.TaskodoroTheme

/**
 * Reusable Task creation/edition form composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskForm(
    title: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    @StringRes titleError: Int?,
    description: TextFieldValue,
    onDescriptionChange: (TextFieldValue) -> Unit,
    priority: Int,
    onPriorityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val titleLabel = stringResource(id = R.string.task_form_title)
        val isTitleError = titleError != null
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(titleLabel) },
            placeholder = { Text(titleLabel) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Next
            ),
            isError = isTitleError,
            supportingText = {
                if (isTitleError) Text(
                    text = stringResource(titleError!!), color = MaterialTheme.colorScheme.error
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        val descriptionLabel = stringResource(id = R.string.task_form_description)
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(descriptionLabel) },
            placeholder = { Text(descriptionLabel) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
        )

        Text(
            text = stringResource(id = R.string.task_form_priority),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.paddingFromBaseline(top = 32.dp, bottom = 8.dp)
        )

        SegmentedButton(
            items = getPriorityLabels(),
            selectedItemIndex = priority,
            onSelectedItemChange = onPriorityChange,
            modifier = Modifier.fillMaxWidth()
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
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun TaskFormPreview() {
    TaskodoroTheme {
        TaskForm(
            title = TextFieldValue(),
            onTitleChange = {},
            titleError = null,
            description = TextFieldValue(),
            onDescriptionChange = {},
            priority = 1,
            onPriorityChange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        )
    }
}