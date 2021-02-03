package com.kyant.pixelmusic.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FloatingSheet(
    visible: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val elevation = remember(visible) { androidx.compose.animation.core.Animatable(0f) }.apply {
        LaunchedEffect(visible.value) {
            if (visible.value) {
                animateTo(16f)
            } else {
                animateTo(0f, tween(200, 200))
            }
        }
    }
    Box(Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible.value,
            Modifier.fillMaxSize(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Surface(
                Modifier
                    .fillMaxSize()
                    .pointerInput { detectTapGestures() },
                color = MaterialTheme.colors.surface.copy(0.92f)
            ) {}
        }
        AnimatedVisibility(
            visible.value,
            modifier.align(Alignment.Center),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Card(
                Modifier.padding(48.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = elevation.value.dp
            ) {
                Column {
                    content()
                }
            }
        }
    }
}