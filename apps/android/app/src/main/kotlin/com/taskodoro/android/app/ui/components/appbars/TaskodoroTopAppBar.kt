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

package com.taskodoro.android.app.ui.components.appbars

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.taskodoro.android.R
import com.taskodoro.android.app.ui.components.TaskodoroTooltip
import com.taskodoro.android.app.ui.theme.TaskodoroTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TaskodoroTopAppBar(
    title: String,
    subtitle: String,
    navigationIcon: TopAppBarIcon,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { TitleContent(title, subtitle) },
        navigationIcon = {
            TaskodoroTooltip(navigationIcon.contentDescription) {
                IconButton(
                    onClick = navigationIcon.action,
                    modifier = Modifier
                        .tooltipAnchor(),
                ) {
                    Icon(
                        imageVector = navigationIcon.icon,
                        contentDescription = navigationIcon.contentDescription,
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun TitleContent(title: String, subtitle: String?) {
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
private fun TaskodoroLargeTopBarPreview() {
    TaskodoroTheme {
        TaskodoroTopAppBar(
            title = "Taskodoro TopAppBar",
            subtitle = "A subtitle",
            navigationIcon = TopAppBarIcon(
                icon = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.navigation_back),
                action = { },
            ),
        )
    }
}
