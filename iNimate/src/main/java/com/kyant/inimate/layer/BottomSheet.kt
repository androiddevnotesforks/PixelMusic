package com.kyant.inimate.layer

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    state: SwipeableState<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val elevation = remember(state) { Animatable(0f) }.apply {
        LaunchedEffect(state.targetValue) {
            animateTo(if (state.targetValue) 24f else 0f)
        }
    }
    BoxWithConstraints {
        val progress = (if (state.offset.value.isNaN()) 0f else state.offset.value) /
                constraints.maxHeight.toFloat()
        Card(
            modifier
                .fillMaxSize()
                .offset(y = maxHeight * progress)
                .swipeable(
                    state,
                    mapOf(
                        0f to true,
                        constraints.maxHeight.toFloat() to false
                    ),
                    Orientation.Vertical
                )
                .pointerInput { detectTapGestures {} },
            RoundedCornerShape(0.dp),
            elevation = elevation.value.dp
        ) {
            Column {
                content()
            }
        }
    }
}