package com.kyant.inimate.layer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopSheet(
    visible: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val elevation = remember(visible) { Animatable(0f) }.apply {
        LaunchedEffect(visible.value) {
            if (visible.value) {
                animateTo(24f)
            } else {
                animateTo(0f, tween(200, 200))
            }
        }
    }
    AnimatedVisibility(
        visible.value,
        modifier,
        enter = slideInVertically({ -it }),
        exit = slideOutVertically({ -it })
    ) {
        Card(
            Modifier
                .fillMaxSize()
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