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

package com.taskodoro.android.app.tasks.create

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taskodoro.android.app.R
import com.taskodoro.android.app.ui.components.TaskodoroTemplate
import com.taskodoro.android.app.ui.components.appbars.TaskodoroLargeTopBar
import com.taskodoro.android.app.ui.components.buttons.SegmentedButton
import com.taskodoro.android.app.ui.components.buttons.TaskodoroButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateTaskScreen(
    state: CreateTaskUIState,
    onCreateTaskClick: (title: String, description: String, priority: Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .consumedWindowInsets(WindowInsets.navigationBars)
            .imePadding(),
        topBar = {
            TaskodoroLargeTopBar(
                title = {
                    Text(
                        stringResource(id = R.string.create_new_task_screen_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onNavigationClick = onBackClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            var title by remember { mutableStateOf(TextFieldValue()) }
            var description by remember { mutableStateOf(TextFieldValue()) }
            var priority by remember { mutableStateOf(1) }

            CreateTaskForm(
                title = title,
                onTitleChange = { title = it },
                description = description,
                onDescriptionChange = { description = it },
                priority = priority,
                onPriorityChange = { priority = it },
                titleError = state.titleError
            )

            Spacer(modifier = Modifier.weight(1f))

            state.error?.let {
                Text(
                    text = stringResource(id = state.error),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.errorContainer,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(12.dp)
                )
            }

            TaskodoroButton(
                onClick = { onCreateTaskClick(title.text, description.text, priority) },
                loading = state.loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            {
                Text(stringResource(id = R.string.create_new_task_create_task_button))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskForm(
    title: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    @StringRes titleError: Int?,
    description: TextFieldValue,
    onDescriptionChange: (TextFieldValue) -> Unit,
    priority: Int,
    onPriorityChange: (Int) -> Unit,
) {
    val titleLabel = stringResource(id = R.string.create_new_task_title)
    val isTitleError = titleError != null
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        label = { Text(titleLabel) },
        placeholder = { Text(titleLabel) },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        isError = isTitleError,
        supportingText = {
            if (isTitleError)
                Text(
                    text = stringResource(titleError!!),
                    color = MaterialTheme.colorScheme.error
                )
        },
        modifier = Modifier
            .fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    val descriptionLabel = stringResource(id = R.string.create_new_task_description)
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        label = { Text(descriptionLabel) },
        placeholder = { Text(descriptionLabel) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
    )

    Text(
        text = stringResource(id = R.string.create_new_task_priority),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .paddingFromBaseline(top = 32.dp, bottom = 8.dp)
    )

    SegmentedButton(
        items = getPriorityLabels(),
        selectedItemIndex = priority,
        onSelectedItemChange = onPriorityChange,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun getPriorityLabels(): List<String> = listOf(
    stringResource(id = R.string.create_new_task_priority_low),
    stringResource(id = R.string.create_new_task_priority_medium),
    stringResource(id = R.string.create_new_task_priority_high),
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
private fun CreateTaskScreenPreview() {
    TaskodoroTemplate {
        CreateTaskScreen(
            state = CreateTaskUIState(),
            onCreateTaskClick = { _, _, _ -> },
            onBackClick = {}
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
private fun CreateTaskScreenWithErrorsPreview() {
    TaskodoroTemplate {
        CreateTaskScreen(
            state = CreateTaskUIState(
                titleError = R.string.create_new_task_empty_title_error,
                error = R.string.create_new_task_unknown_error
            ),
            onCreateTaskClick = { _, _, _ -> },
            onBackClick = {}
        )
    }
}