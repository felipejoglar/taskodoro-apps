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

package com.taskodoro.android.app.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taskodoro.android.app.tasks.create.TaskCreateScreen
import com.taskodoro.android.app.tasks.create.TaskCreateViewModel
import com.taskodoro.android.app.ui.components.AppTemplate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val viewModel = viewModel<TaskCreateViewModel>()

            val state by viewModel.state.collectAsStateWithLifecycle()
            var openConfirmationDialog by remember { mutableStateOf(false) }

            val onBackClicked = {
                if (state.title.isNotBlank()) {
                    openConfirmationDialog = true
                } else {
                    finish()
                }
            }

            AppTemplate {
                TaskCreateScreen(
                    state = state,
                    openConfirmationDialog = openConfirmationDialog,
                    onTitleChanged = viewModel::onTitleChanged,
                    onDescriptionChanged = viewModel::onDescriptionChanged,
                    onDueDateChanged = viewModel::onDueDateChanged,
                    onTaskCreateClicked = viewModel::onTaskCreateClicked,
                    onTaskCreated = {
                        Toast.makeText(this, "Task created!!", Toast.LENGTH_SHORT).show()
                    },
                    onErrorShown = viewModel::onErrorShown,
                    onDismissConfirmationDialog = { openConfirmationDialog = false },
                    onDiscardChanges = ::finish,
                    onBackClicked = onBackClicked,
                )
            }

            BackHandler(enabled = true, onBackClicked)
        }
    }
}
