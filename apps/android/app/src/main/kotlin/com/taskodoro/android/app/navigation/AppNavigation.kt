/*
 *    Copyright 2024 Felipe Joglar
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

package com.taskodoro.android.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.taskodoro.android.app.di.DiContainer
import com.taskodoro.android.app.navigation.graphs.onboarding.ONBOARDING_GRAPH_ROUTE
import com.taskodoro.android.app.navigation.graphs.onboarding.navigateToOnboarding
import com.taskodoro.android.app.navigation.graphs.onboarding.onboardingGraph
import com.taskodoro.android.app.navigation.graphs.tasks.TASK_GRAPH_ROUTE
import com.taskodoro.android.app.navigation.graphs.tasks.navigateToTaskGraph
import com.taskodoro.android.app.navigation.graphs.tasks.taskGraph
import com.taskodoro.android.app.navigation.transitions.forwardBackwardNavTransition
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun AppNavigation(
    di: DiContainer,
    modifier: Modifier = Modifier,
) {
    val onboardingStore = di.onboardingStore

    PreComposeApp {
        val navigator = rememberNavigator()
        val scope = rememberCoroutineScope()

        val isOnboarded = runBlocking { onboardingStore.isOnboarded().getOrThrow() }
        val initialRoute = if (isOnboarded) TASK_GRAPH_ROUTE else ONBOARDING_GRAPH_ROUTE

        NavHost(
            navigator = navigator,
            initialRoute = initialRoute,
            navTransition = forwardBackwardNavTransition(),
            modifier = modifier,
        ) {
            onboardingGraph(
                onContinueClicked = {
                    scope.launch { onboardingStore.setOnboarded() }

                    val options = NavOptions(popUpTo = PopUpTo.First())
                    navigator.navigateToTaskGraph(options)
                },
            )

            taskGraph(
                taskNew = di.taskNewUseCase,
                navigator = navigator,
                onKnowMoreClicked = { navigator.navigateToOnboarding() },
            )
        }
    }
}
