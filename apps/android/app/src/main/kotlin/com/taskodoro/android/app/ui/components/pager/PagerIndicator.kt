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

package com.taskodoro.android.app.ui.components.pager

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taskodoro.android.app.ui.components.preview.ComponentPreviews
import com.taskodoro.android.app.ui.components.preview.DynamicColorsPreviews
import com.taskodoro.android.app.ui.theme.AppTheme

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    gapSize: Dp = 4.dp,
) {
    Row(
        modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { iteration ->
            val isSelected = currentPage == iteration

            val startColor by gradientStartColor(isSelected = isSelected)
            val endColor by gradientEndColor(isSelected = isSelected)
            val width by width(isSelected = isSelected, dotSize = dotSize)

            Box(
                modifier = Modifier
                    .padding(gapSize)
                    .clip(RoundedCornerShape(50))
                    .background(Brush.Companion.linearGradient(listOf(startColor, endColor)))
                    .height(dotSize)
                    .width(width),
            )
        }
    }
}

@Composable
private fun gradientStartColor(isSelected: Boolean) = animateColorAsState(
    targetValue = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface
    },
    label = "Gradient start color animation",
)

@Composable
private fun gradientEndColor(isSelected: Boolean) = animateColorAsState(
    targetValue = if (isSelected) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.onSurface
    },
    label = "Gradient end color animation",
)

@Composable
private fun width(
    isSelected: Boolean,
    dotSize: Dp,
): State<Dp> = animateDpAsState(
    targetValue = if (isSelected) {
        dotSize.times(2)
    } else {
        dotSize
    },
    label = "Size animation",
)

@ComponentPreviews
@Composable
private fun PagerPreview() {
    AppTheme(
        useDynamicColors = false,
    ) {
        Surface {
            PagerIndicator(
                pageCount = 5,
                currentPage = 2,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@DynamicColorsPreviews
@Composable
private fun PagerDynamicColorPreview() {
    AppTheme {
        Surface {
            PagerIndicator(
                pageCount = 5,
                currentPage = 2,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}
