package com.kyant.inimate.layer

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.statusBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForeLayer(
    state: SwipeableState<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    BoxWithConstraints {
        val progress = state.progress(constraints)
        Card(
            modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 48.dp)
                .offset(y = maxHeight * (1f - progress))
                .swipeable(
                    state,
                    mapOf(
                        0f to true,
                        constraints.maxHeight.toFloat() to false
                    ),
                    Orientation.Vertical
                )
                .pointerInput { detectTapGestures {} },
            RoundedCornerShape((16.dp * progress).coerceAtLeast(0.dp)),
            elevation = 24.dp * progress
        ) {
            Column {
                content()
            }
        }
    }
}