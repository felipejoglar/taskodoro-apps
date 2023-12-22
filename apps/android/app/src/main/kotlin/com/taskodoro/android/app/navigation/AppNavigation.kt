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

package com.taskodoro.android.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.taskodoro.android.app.navigation.graphs.onboarding.OnboardingRoute
import com.taskodoro.android.app.navigation.graphs.onboarding.onboardingGraph
import com.taskodoro.android.app.navigation.graphs.tasks.navigateToTaskGraph
import com.taskodoro.android.app.navigation.graphs.tasks.taskGraph
import com.taskodoro.android.app.navigation.transitions.forwardBackwardNavTransition
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
) {
    PreComposeApp {
        val navigator = rememberNavigator()
        val context = LocalContext.current

        NavHost(
            navigator = navigator,
            initialRoute = OnboardingRoute,
            navTransition = forwardBackwardNavTransition(),
            modifier = modifier,
        ) {
            onboardingGraph(
                onContinueClicked = {
                    val options = NavOptions(popUpTo = PopUpTo.First())
                    navigator.navigateToTaskGraph(options)
                },
            )

            taskGraph(context, navigator)
        }
    }
}
