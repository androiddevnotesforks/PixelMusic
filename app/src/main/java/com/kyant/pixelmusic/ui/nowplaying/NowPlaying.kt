package com.kyant.pixelmusic.ui.nowplaying

import android.graphics.Bitmap
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.kyant.inimate.layer.progress
import com.kyant.inimate.util.lighten
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxWithConstraintsScope.NowPlaying(
    state: SwipeableState<Boolean>,
    playlistState: SwipeableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val player = LocalPixelPlayer.current
    val song = LocalNowPlaying.current
    val isLight = MaterialTheme.colors.isLight
    var contentState by remember { mutableStateOf(NowPlayingContentState.SONG) }
    val progress = state.progress(constraints).coerceIn(0f..1f)
    var horizontalDragOffset by remember { mutableStateOf(0f) }
    val defaultBackgroundColor = MaterialTheme.colors.surface
    var backgroundColor by remember { mutableStateOf(defaultBackgroundColor) }

    LaunchedEffect(song.icon) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            song.icon?.copy(Bitmap.Config.ARGB_8888, true)?.let { bitmap ->
                Palette.from(bitmap).generate { palette ->
                    if (isLight) {
                        palette?.lightMutedSwatch?.rgb?.let {
                            backgroundColor = Color(it)
                        }
                    } else {
                        palette?.darkMutedSwatch?.rgb?.let {
                            backgroundColor = Color(it)
                        }
                    }
                }
            }
        }
    }

    Card(
        modifier
            .preferredSize(
                256.dp + (maxWidth - 256.dp) * progress,
                72.dp + (maxHeight - 72.dp) * progress
            )
            .align(Alignment.BottomStart)
            .offset(16.dp * (1f - progress), (-64).dp * (1f - progress))
            .swipeable(
                state,
                mapOf(
                    0f to true,
                    constraints.maxHeight.toFloat() to false
                ),
                Orientation.Vertical
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { playlistState.animateTo(!playlistState.value) }
                ) {
                    state.animateTo(true)
                }
            },
        shape = RoundedCornerShape(16.dp * (1f - progress)),
        backgroundColor = backgroundColor.lighten(0.4f * (1f - progress)),
        elevation = 1.dp * (1f - progress)
    ) {
        BoxWithConstraints {
            NowPlayingExpanded(
                Modifier.alpha(progress),
                contentState,
                onTabClick = { contentState = it }
            )
            NowPlayingCollapsed(
                state,
                contentState,
                Modifier.draggable(
                    Orientation.Horizontal,
                    onDragStopped = { horizontalDragOffset = 0f }
                ) {
                    horizontalDragOffset += it
                    when {
                        horizontalDragOffset <= -48.dp.toPx() -> player.next()
                        horizontalDragOffset >= 48.dp.toPx() -> player.previous()
                    }
                }
            )
        }
    }
}