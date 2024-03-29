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

package com.taskodoro.android.app.ui.components.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.Tooltip
import com.taskodoro.android.app.ui.components.appbar.model.ActionsList
import com.taskodoro.android.app.ui.components.appbar.model.OverflowList
import com.taskodoro.android.app.ui.components.appbar.model.TopAppBarAction
import com.taskodoro.android.app.ui.components.buttons.IconButton
import com.taskodoro.android.app.ui.components.buttons.TextButton
import com.taskodoro.android.app.ui.components.icons.ArrowBack
import com.taskodoro.android.app.ui.components.icons.Icons
import com.taskodoro.android.app.ui.components.icons.Overflow
import com.taskodoro.android.app.ui.components.icons.Send
import com.taskodoro.android.app.ui.components.preview.ComponentPreviews
import com.taskodoro.android.app.ui.components.preview.DynamicColorsPreviews
import com.taskodoro.android.app.ui.components.preview.FontScalePreviews
import com.taskodoro.android.app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    navigationIcon: TopAppBarAction.Icon?,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    actions: ActionsList = ActionsList(listOf()),
    overflow: OverflowList = OverflowList(listOf()),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { TitleContent(title, subtitle) },
        navigationIcon = { navigationIcon?.let { TopAppBarIcon(it) } },
        actions = { TopAppBarActions(actions, overflow) },
        modifier = modifier,
    )
}

@Composable
private fun TitleContent(
    title: String,
    subtitle: String?,
) {
    Column {
        subtitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun TopAppBarActions(
    actions: ActionsList,
    overflow: OverflowList,
) {
    actions.items.map { element ->
        when (element) {
            is TopAppBarAction.Button -> TopAppBarButton(button = element)
            is TopAppBarAction.Icon -> TopAppBarIcon(icon = element)
        }
    }

    if (overflow.items.isNotEmpty()) {
        OverflowMenu(overflow)
    }
}

@Composable
private fun TopAppBarIcon(
    icon: TopAppBarAction.Icon,
    modifier: Modifier = Modifier,
) {
    if (icon.isLoading) {
        CircularProgressIndicator(
            strokeCap = StrokeCap.Round,
            modifier = modifier
                .padding(4.dp)
                .size(40.dp)
                .padding(4.dp),
        )
    } else {
        IconButton(
            onClick = icon.action,
            icon = icon.icon,
            contentDescription = icon.contentDescription,
            modifier = modifier,
            enabled = icon.enabled,
            iconTint = icon.tint,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarButton(
    button: TopAppBarAction.Button,
    modifier: Modifier = Modifier,
) {
    Tooltip(button.text) {
        TextButton(
            onClick = button.action,
            modifier = modifier,
        ) {
            Text(text = button.text.uppercase())
        }
    }
}

@Composable
private fun OverflowMenu(
    overflow: OverflowList,
    modifier: Modifier = Modifier,
) {
    var isOverflowExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopEnd),
    ) {
        TopAppBarIcon(
            TopAppBarAction.Icon(
                icon = Icons.Overflow,
                contentDescription = stringResource(id = R.string.top_app_bar_overflow_button),
                tint = MaterialTheme.colorScheme.primary,
                action = { isOverflowExpanded = true },
            ),
        )

        DropdownMenu(
            expanded = isOverflowExpanded,
            onDismissRequest = { isOverflowExpanded = false },
            offset = DpOffset(x = 16.dp, y = 0.dp),
        ) {
            overflow.items.forEach { overflowItem ->
                val onClick = {
                    isOverflowExpanded = false
                    overflowItem.action()
                }

                if (overflowItem.icon != null) {
                    DropdownMenuItem(
                        text = { Text(overflowItem.text) },
                        onClick = onClick,
                        leadingIcon = {
                            Icon(
                                imageVector = overflowItem.icon,
                                contentDescription = overflowItem.text,
                            )
                        },
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text(overflowItem.text) },
                        onClick = onClick,
                    )
                }
            }
        }
    }
}

@ComponentPreviews
@Composable
private fun TopAppBarPreview() {
    AppTheme(useDynamicColors = false) {
        TopAppBarCommonPreview()
    }
}

@DynamicColorsPreviews
@Composable
private fun TopAppBarDynamicColorsPreview() {
    AppTheme {
        TopAppBarCommonPreview()
    }
}

@FontScalePreviews
@Composable
private fun TopAppBarFontScalePreview() {
    AppTheme(useDynamicColors = false) {
        TopAppBarCommonPreview()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarCommonPreview() {
    TopAppBar(
        title = "TopAppBar",
        subtitle = "A subtitle",
        navigationIcon = TopAppBarAction.Icon(
            icon = Icons.ArrowBack,
            contentDescription = "Back",
            action = { },
        ),
        actions = ActionsList(
            listOf(
                TopAppBarAction.Icon(
                    icon = Icons.Send,
                    contentDescription = "Submit",
                    action = { },
                    tint = MaterialTheme.colorScheme.primary,
                ),
                TopAppBarAction.Button(
                    text = "Save",
                    action = { },
                ),
            ),
        ),
    )
}
