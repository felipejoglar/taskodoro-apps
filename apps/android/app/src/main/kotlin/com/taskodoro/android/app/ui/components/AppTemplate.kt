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

package com.taskodoro.android.app.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.taskodoro.android.app.ui.components.preview.ScreenPreviews
import com.taskodoro.android.app.ui.theme.AppTheme

@Composable
fun AppTemplate(
    modifier: Modifier = Modifier,
    useDarkIcons: Boolean = !isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    AppTheme {
        val systemUiController = rememberSystemUiController()

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
        )

        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = modifier
                .fillMaxSize(),
        ) {
            content()
        }
    }
}

@ScreenPreviews
@Composable
private fun AppTemplatePreview() {
    AppTemplate {
        Column {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "Taskodoro",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}
