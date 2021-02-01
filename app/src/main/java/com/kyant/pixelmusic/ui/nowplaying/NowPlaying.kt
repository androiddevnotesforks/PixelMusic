package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.player.Playlist

@Composable
fun BoxWithConstraintsScope.NowPlaying(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    var state by remember { mutableStateOf(NowPlayingState.COLLAPSED) }
    var contentState by remember { mutableStateOf(NowPlayingContentState.SONG) }
    var playlistDisplayed by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }
    val transition = updateTransition(state)
    val width by transition.animateDp {
        when (it) {
            NowPlayingState.COLLAPSED -> 256.dp
            NowPlayingState.EXPANDED -> maxWidth
        }
    }
    val height by transition.animateDp({ spring(stiffness = 1200f) }) {
        when (it) {
            NowPlayingState.COLLAPSED -> 72.dp
            NowPlayingState.EXPANDED -> maxHeight
        }
    }
    val offset by transition.animateIntOffset({ spring(0.8f, 800f) }) {
        with(density) {
            when (it) {
                NowPlayingState.COLLAPSED -> IntOffset(16.dp.roundToPx(), -64.dp.roundToPx())
                NowPlayingState.EXPANDED -> IntOffset.Zero
            }
        }
    }
    val cornerSize by transition.animateDp {
        when (it) {
            NowPlayingState.COLLAPSED -> 16.dp
            NowPlayingState.EXPANDED -> 0.dp
        }
    }
    val elevation by transition.animateDp {
        when (it) {
            NowPlayingState.COLLAPSED -> 2.dp
            NowPlayingState.EXPANDED -> 0.dp
        }
    }
    val alpha by transition.animateFloat({ tween(200, 50) }) {
        when (it) {
            NowPlayingState.COLLAPSED -> 0f
            NowPlayingState.EXPANDED -> 1f
        }
    }

    Card(
        modifier
            .preferredSize(width, height)
            .align(Alignment.BottomStart)
            .offset { offset }
            .pointerInput {
                detectTapGestures {
                    state = NowPlayingState.EXPANDED
                }
            }
            .draggable(Orientation.Vertical, onDragStopped = {
                dragOffset = 0f
            }) {
                dragOffset += it
                when {
                    dragOffset <= -64.dp.toPx() -> state = NowPlayingState.EXPANDED
                    dragOffset >= 64.dp.toPx() -> state = NowPlayingState.COLLAPSED
                }
            },
        shape = RoundedCornerShape(cornerSize),
        elevation = elevation
    ) {
        Box {
            if (state == NowPlayingState.EXPANDED) {
                NowPlayingExpanded(
                    Modifier.alpha(alpha),
                    contentState,
                    onPlaylistButtonClick = { playlistDisplayed = true },
                    onTabClick = { contentState = it }
                )
            }
            NowPlayingCollapsed(state, contentState)
        }
    }
    if (playlistDisplayed) {
        Playlist()
    }
}