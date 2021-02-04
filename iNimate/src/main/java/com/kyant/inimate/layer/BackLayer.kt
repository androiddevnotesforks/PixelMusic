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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.LocalSysUiController
import com.kyant.inimate.insets.LocalWindowInsets
import com.kyant.inimate.insets.statusBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackLayer(
    state: SwipeableState<Boolean>?,
    darkIcons: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val systemUiController = LocalSysUiController.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val progress = state?.progress(constraints) ?: 0f
        Surface(
            modifier
                .fillMaxSize()
                .padding(top = (with(density) { LocalWindowInsets.current.statusBars.top.toDp() } * progress - 8.dp * progress)
                    .coerceAtLeast(0.dp))
                .scale((screenWidth - 24.dp * progress) / screenWidth),
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
        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons)
    }
}