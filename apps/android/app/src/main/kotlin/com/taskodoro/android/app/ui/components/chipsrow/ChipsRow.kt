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

package com.taskodoro.android.app.ui.components.chipsrow

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.chipsrow.model.ChipsList
import com.taskodoro.android.app.ui.components.chipsrow.model.ChipsRowItem
import com.taskodoro.android.app.ui.components.preview.ComponentPreviews
import com.taskodoro.android.app.ui.components.preview.FontScalePreviews
import com.taskodoro.android.app.ui.theme.AppTheme

private const val GRADIENT_START = 0.0f
private const val GRADIENT_MIDDLE = 0.5f
private const val GRADIENT_END = 1.0f

@Composable
fun ChipsRow(
    chips: ChipsList,
    modifier: Modifier = Modifier,
    showTopShadow: Boolean = false,
) {
    if (chips.items.isEmpty()) return

    Box(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            chips.items.map { field ->
                Chip(field)
            }
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

        if (showTopShadow) {
            Spacer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .shadow(1.dp)
                    .height(1.dp),
            )
        }
    }
}

@Composable
private fun Chip(
    item: ChipsRowItem,
) {
    AssistChip(
        onClick = item.onClick,
        label = {
            Text(
                text = item.description,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = item.icon,
                contentDescription = item.description,
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

@FontScalePreviews
@ComponentPreviews
@Composable
private fun ChipsRowPreview() {
    val item = ChipsRowItem(
        icon = Icons.Rounded.CalendarMonth,
        description = stringResource(id = R.string.create_new_task_due_date),
        onClick = {},
    )
    val chips = ChipsList(listOf(item, item, item, item))

    AppTheme {
        ChipsRow(
            chips = chips,
        )
    }
}
