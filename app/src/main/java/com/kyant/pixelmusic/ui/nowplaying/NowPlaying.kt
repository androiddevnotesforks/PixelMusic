package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.kyant.inimate.layer.progress
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.ui.player.PlayerPlaylist

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxWithConstraintsScope.NowPlaying(modifier: Modifier = Modifier) {
    val player = LocalPixelPlayer.current
    val state = rememberSwipeableState(false)
    var contentState by remember { mutableStateOf(NowPlayingContentState.SONG) }
    val playlistState = rememberSwipeableState(false)
    val progress = state.progress(constraints).coerceIn(0f..1f)
    var horizontalDragOffset by remember { mutableStateOf(0f) }

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
            .pointerInput {
                detectTapGestures(
                    onLongPress = { playlistState.animateTo(!playlistState.value) }
                ) {
                    state.animateTo(true)
                }
            },
        shape = RoundedCornerShape(16.dp * (1f - progress)),
        elevation = 1.dp * (1f - progress)
    ) {
        BoxWithConstraints {
            NowPlayingExpanded(
                Modifier.alpha(progress),
                contentState,
                onPlaylistButtonClick = { playlistState.animateTo(true) },
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
    PlayerPlaylist(playlistState)
}