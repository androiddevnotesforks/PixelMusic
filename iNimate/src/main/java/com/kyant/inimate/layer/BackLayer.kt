package com.kyant.inimate.layer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.LocalSysUiController
import com.kyant.inimate.insets.LocalWindowInsets
import com.kyant.inimate.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackLayer(
    state: SwipeableState<Boolean>?,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val systemUiController = LocalSysUiController.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val isLight = MaterialTheme.colors.isLight
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
        LaunchedEffect(progress, isLight) {
            CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                systemUiController.setSystemBarsColor(
                    Color.Transparent,
                    if (isLight) progress <= 0.5f else false
                )
            }
        }
    }
}