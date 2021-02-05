package com.kyant.inimate.layer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.LocalSysUiController
import com.kyant.inimate.insets.LocalWindowInsets
import com.kyant.inimate.insets.statusBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackLayer(
    states: List<SwipeableState<Boolean>>,
    darkIcons: (Float) -> Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val systemUiController = LocalSysUiController.current
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val progress = states.map { it.progress(constraints) }.maxByOrNull { it } ?: 0f
        Surface(
            modifier
                .fillMaxSize()
                .padding(top = (with(density) { LocalWindowInsets.current.statusBars.top.toDp() } * progress - 8.dp * progress)
                    .coerceAtLeast(0.dp))
                .scale((maxWidth - 24.dp * progress) / maxWidth),
            RoundedCornerShape((12.dp * progress).coerceAtLeast(0.dp))
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                content()
            }
        }
        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons(progress))
    }
}