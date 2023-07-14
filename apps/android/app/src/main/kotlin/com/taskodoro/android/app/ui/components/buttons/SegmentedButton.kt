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

package com.taskodoro.android.app.ui.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taskodoro.android.app.ui.theme.TaskodoroTheme

/**
 * <a href="https://m3.material.io/components/segmented-buttons/overview" class="external" target="_blank">Material Design segmented button</a>.
 *
 * Segmented buttons help people select options, switch views, or sort elements. Segmented buttons
 * can have 2-5 segments. Each segment is clearly divided and contains label text.
 *
 * Use a single-select segmented button to select one option from a set, switch between views, or
 * sort elements from up to five options.
 * For example, use a single-select segmented button to choose one of a set of sizes, such as this
 * beverage size selector.
 *
 * @param items the labels each segment should show. Its size determines the number of segments
 * @param selectedItemIndex the selected item index used to highlight the corresponding segment
 * @param onSelectedItemChange the callback that is triggered when a segment is selected. The
 * [selectedItemIndex] comes as a parameter of the callback
 * @param modifier the [Modifier] to be applied to this text field
 */
@Composable
fun SegmentedButton(
    items: ItemsList,
    selectedItemIndex: Int,
    onSelectedItemChange: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        items.value.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .offset((-1 * index).dp, 0.dp),
                onClick = { onSelectedItemChange(index) },
                shape = when (index) {
                    0 -> RoundedCornerShape(
                        topStartPercent = 50,
                        bottomStartPercent = 50,
                    )

                    items.value.lastIndex -> RoundedCornerShape(
                        topEndPercent = 50,
                        bottomEndPercent = 50,
                    )

                    else -> RoundedCornerShape(0)
                },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                colors = if (selectedItemIndex == index) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                            .copy(alpha = 0.4f),
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                },
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = if (selectedItemIndex == index) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                )
            }
        }
    }
}

@Immutable
data class ItemsList(
    val value: List<String>,
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
private fun SegmentedButtonPreview() {
    TaskodoroTheme {
        SegmentedButton(
            items = ItemsList(listOf("One", "Two Bigger", "Three", "Four")),
            selectedItemIndex = 1,
            onSelectedItemChange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }
}
