package com.kyant.pixelmusic.ui.my

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.History
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.component.TwoToneCard
import com.kyant.pixelmusic.ui.theme.androidBlue
import com.kyant.pixelmusic.ui.theme.androidOrange

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BoxWithConstraintsScope.My(
    visible: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
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
        enter = fadeIn() + slideInVertically({ with(density) { -(maxHeight + 16.dp).roundToPx() } }),
        exit = fadeOut()
    ) {
        Card(
            Modifier.padding(48.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 16.dp
        ) {
            Column {
                IconButton({ visible.value = false }) {
                    Icon(Icons.Outlined.Close, "Close")
                }
                LazyColumn {
                    item {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            RoundedCornerShape(16.dp),
                            MaterialTheme.colors.primary,
                            elevation = 0.dp
                        ) {
                            Column(
                                Modifier
                                    .clickable {}
                                    .padding(32.dp)
                            ) {
                                Text(
                                    "Log in to explore more",
                                    style = MaterialTheme.typography.h5
                                )
                            }
                        }
                    }
                    item {
                        TwoToneCard(
                            androidBlue,
                            "History",
                            Icons.Outlined.History, "History"
                        )
                    }
                    item {
                        TwoToneCard(
                            androidOrange,
                            "Favorites",
                            Icons.Outlined.Favorite, "Favorites"
                        )
                    }
                }
            }
        }
    }
}