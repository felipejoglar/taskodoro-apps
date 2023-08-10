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

package com.taskodoro.android.app.ui.components.appbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taskodoro.android.app.ui.components.TaskodoroTooltip
import com.taskodoro.android.app.ui.components.appbar.model.ActionsList
import com.taskodoro.android.app.ui.components.appbar.model.TopAppBarElement
import com.taskodoro.android.app.ui.theme.TaskodoroTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TaskodoroTopAppBar(
    title: String,
    navigationIcon: TopAppBarElement.Icon,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    actions: ActionsList = ActionsList(listOf()),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { TitleContent(title, subtitle) },
        navigationIcon = { TopAppBarIcon(navigationIcon) },
        actions = {
            actions.elements.map { element ->
                when (element) {
                    is TopAppBarElement.Button -> TopAppBarButton(button = element)
                    is TopAppBarElement.Icon -> TopAppBarIcon(icon = element)
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarIcon(
    icon: TopAppBarElement.Icon,
    modifier: Modifier = Modifier,
) {
    TaskodoroTooltip(icon.contentDescription) {
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
                modifier = modifier
                    .tooltipAnchor(),
            ) {
                Icon(
                    imageVector = icon.icon,
                    contentDescription = icon.contentDescription,
                    tint = icon.tint ?: LocalContentColor.current,
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarButton(
    button: TopAppBarElement.Button,
    modifier: Modifier = Modifier,
) {
    TaskodoroTooltip(button.text) {
        TextButton(
            onClick = button.action,
            modifier = modifier
                .tooltipAnchor(),
        ) {
            Text(text = button.text.uppercase())
        }
    }
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
                color = MaterialTheme.colorScheme.outline,
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

@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TaskodoroTopBarPreview() {
    TaskodoroTheme {
        TaskodoroTopAppBar(
            title = "Taskodoro TopAppBar",
            subtitle = "A subtitle",
            navigationIcon = TopAppBarElement.Icon(
                icon = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                action = { },
            ),
            actions = ActionsList(
                listOf(
                    TopAppBarElement.Icon(
                        icon = Icons.Rounded.Send,
                        contentDescription = "Submit",
                        action = { },
                        tint = MaterialTheme.colorScheme.primary,
                    ),
                    TopAppBarElement.Button(
                        text = "Save",
                        action = { },
                    ),
                ),
            ),
        )
    }
}
