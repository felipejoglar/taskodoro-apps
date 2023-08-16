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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taskodoro.android.app.ui.components.Tooltip
import com.taskodoro.android.app.ui.components.appbar.model.ActionsList
import com.taskodoro.android.app.ui.components.appbar.model.TopAppBarAction
import com.taskodoro.android.app.ui.components.preview.ComponentPreviews
import com.taskodoro.android.app.ui.components.preview.FontScalePreviews
import com.taskodoro.android.app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    navigationIcon: TopAppBarAction.Icon,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    actions: ActionsList = ActionsList(listOf()),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { TitleContent(title, subtitle) },
        navigationIcon = { TopAppBarIcon(navigationIcon) },
        actions = { TopAppBarActions(actions) },
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
private fun TopAppBarActions(actions: ActionsList) {
    actions.items.map { element ->
        when (element) {
            is TopAppBarAction.Button -> TopAppBarButton(button = element)
            is TopAppBarAction.Icon -> TopAppBarIcon(icon = element)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarIcon(
    icon: TopAppBarAction.Icon,
    modifier: Modifier = Modifier,
) {
    Tooltip(icon.contentDescription) {
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
                modifier = modifier,
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

@OptIn(ExperimentalMaterial3Api::class)
@FontScalePreviews
@ComponentPreviews
@Composable
private fun TaskodoroTopBarPreview() {
    AppTheme {
        TopAppBar(
            title = "TopAppBar",
            subtitle = "A subtitle",
            navigationIcon = TopAppBarAction.Icon(
                icon = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                action = { },
            ),
            actions = ActionsList(
                listOf(
                    TopAppBarAction.Icon(
                        icon = Icons.Rounded.Send,
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
}
