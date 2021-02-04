package com.kyant.inimate.layer

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.LocalSysUiController
import com.kyant.inimate.insets.LocalWindowInsets
import com.kyant.inimate.insets.statusBarsPadding
import kotlinx.coroutines.*

@Composable
fun BackLayer(
    backed: Boolean,
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val systemUiController = LocalSysUiController.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val isLight = MaterialTheme.colors.isLight
    val transition = updateTransition(backed)
    val statusBarPadding by transition.animateDp({ tween(500) }) {
        with(density) {
            if (backed) LocalWindowInsets.current.statusBars.top.toDp() else 0.dp
        }
    }
    val padding by transition.animateDp({ tween(500) }) {
        if (backed) 24.dp else 0.dp
    }
    val cornerSize by transition.animateDp({ tween(500) }) {
        if (backed) 16.dp else 0.dp
    }
    Surface(
        Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Surface(
            modifier
                .fillMaxSize()
                .padding(top = statusBarPadding - padding / 2)
                .scale((screenWidth - padding) / screenWidth),
            RoundedCornerShape(cornerSize)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                content()
            }
        }
    }
    LaunchedEffect(backed, isLight) {
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
            delay(if (backed) 150 else 250)
            systemUiController.setSystemBarsColor(
                Color.Transparent,
                if (isLight) !backed else false
            )
        }
    }
}