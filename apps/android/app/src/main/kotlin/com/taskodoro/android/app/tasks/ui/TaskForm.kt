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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.TextField
import com.taskodoro.android.app.ui.components.chipsrow.ChipsRow
import com.taskodoro.android.app.ui.components.chipsrow.model.ChipsList
import com.taskodoro.android.app.ui.components.chipsrow.model.ChipsRowItem
import com.taskodoro.android.app.ui.components.preview.ComponentPreviews
import com.taskodoro.android.app.ui.components.preview.FontScalePreviews
import com.taskodoro.android.app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskForm(
    title: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDueDateChanged: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    var openDueDateSelection by rememberSaveable { mutableStateOf(false) }
    var initialSelectedDateSeconds by rememberSaveable { mutableStateOf<Long?>(null) }

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

        ChipsRow(
            chips = ChipsList(
                listOf(
                    dueDateField(
                        onDueDateClicked = { openDueDateSelection = true },
                    ),
                ),
            ),
            showTopShadow = scrollState.canScrollForward,
        )
    }

    if (openDueDateSelection) {
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val onDismiss = { openDueDateSelection = false }

        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            DueDateSelection(
                onDateSelected = {
                    initialSelectedDateSeconds = it
                    onDueDateChanged(it)
                    openDueDateSelection = false
                },
                onDismiss = onDismiss,
                initialSelectedDateSeconds = initialSelectedDateSeconds,
            )
        }
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

    TextField(
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
    TextField(
        value = description,
        onValueChanged = onDescriptionChanged,
        placeHolderText = descriptionLabel,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        modifier = Modifier
            .defaultMinSize(minHeight = 128.dp),
    )
}

@Composable
fun dueDateField(
    onDueDateClicked: () -> Unit,
) = ChipsRowItem(
    icon = Icons.Rounded.CalendarMonth,
    description = stringResource(id = R.string.create_new_task_due_date),
    onClick = onDueDateClicked,
)

@FontScalePreviews
@ComponentPreviews
@Composable
private fun TaskFormPreview() {
    AppTheme {
        TaskForm(
            title = "",
            onTitleChanged = {},
            description = "",
            onDescriptionChanged = {},
            onDueDateChanged = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        )
    }
}
