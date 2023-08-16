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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.appbar.SingleRowTopAppBar
import com.taskodoro.android.app.ui.components.appbar.model.ActionsList
import com.taskodoro.android.app.ui.components.appbar.model.TopAppBarAction
import com.taskodoro.android.app.ui.components.preview.ComponentPreviews
import com.taskodoro.android.app.ui.components.preview.DynamicColorsPreviews
import com.taskodoro.android.app.ui.components.preview.FontScalePreviews
import com.taskodoro.android.app.ui.theme.AppTheme
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DueDateSelection(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedDateSeconds: Long? = null,
) {
    Column(
        modifier = modifier,
    ) {
        val datePickerState = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = initialSelectedDateSeconds?.asMillis() ?: today,
        )

        SingleRowTopAppBar(
            title = stringResource(id = R.string.create_new_task_due_date),
            navigationIcon = TopAppBarAction.Icon(
                icon = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.navigation_back),
                action = onDismiss,
            ),
            actions = ActionsList(
                listOf(
                    TopAppBarAction.Button(
                        text = "Save",
                    ) {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(it.asSeconds())
                        }
                        onDismiss()
                    },
                ),
            ),
        )

        DatePicker(
            state = datePickerState,
            title = null,
            dateValidator = { it >= today },
            showModeToggle = false,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

private val today: Long
    get() = TimeUnit.SECONDS.toMillis(LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC))

private fun Long.asSeconds() = TimeUnit.MILLISECONDS.toSeconds(this)
private fun Long.asMillis() = TimeUnit.SECONDS.toMillis(this)

@ComponentPreviews
@Composable
private fun DueDateSelectionPreview() {
    AppTheme(useDynamicColors = false) {
        DueDateSelectionCommonPreview()
    }
}

@DynamicColorsPreviews
@Composable
private fun DueDateSelectionDynamicColorsPreview() {
    AppTheme {
        DueDateSelectionCommonPreview()
    }
}

@FontScalePreviews
@Composable
private fun DueDateSelectionFontScalePreview() {
    AppTheme(useDynamicColors = false) {
        DueDateSelectionCommonPreview()
    }
}

@Composable
private fun DueDateSelectionCommonPreview() {
    Surface {
        DueDateSelection(
            onDateSelected = {},
            onDismiss = {},
        )
    }
}
