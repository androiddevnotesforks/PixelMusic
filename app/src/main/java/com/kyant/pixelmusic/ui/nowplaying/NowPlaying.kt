package com.kyant.pixelmusic.ui.nowplaying

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.palette.graphics.Palette
import com.kyant.inimate.layer.progress
import com.kyant.inimate.util.lighten
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.ui.component.ProgressBar
import com.kyant.pixelmusic.ui.player.PlayController
import com.kyant.pixelmusic.ui.shape.SmoothRoundedCornerShape
import com.kyant.pixelmusic.ui.song.Cover
import com.kyant.pixelmusic.util.LaunchedIOEffectUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxWithConstraintsScope.NowPlaying(
    state: SwipeableState<Boolean>,
    playlistState: SwipeableState<Boolean>,
    lyricsState: SwipeableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val player = LocalPixelPlayer.current
    val song = LocalNowPlaying.current
    val isLight = MaterialTheme.colors.isLight
    val progress = state.progress(constraints).coerceIn(0f..1f)
    var horizontalDragOffset by remember { mutableStateOf(0f) }
    val defaultBackgroundColor = MaterialTheme.colors.surface
    var backgroundColor by remember { mutableStateOf(defaultBackgroundColor) }

    song.icon.LaunchedIOEffectUnit {
        song.icon?.asAndroidBitmap()?.copy(Bitmap.Config.ARGB_8888, true)?.let { bitmap ->
            Palette.from(bitmap).generate { palette ->
                if (isLight) {
                    palette?.lightMutedSwatch?.rgb?.let { backgroundColor = Color(it) }
                } else {
                    palette?.darkMutedSwatch?.rgb?.let { backgroundColor = Color(it) }
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
                    onLongPress = {
                        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                            playlistState.animateTo(!playlistState.currentValue)
                        }
                    }
                ) {
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        state.animateTo(true)
                    }
                }
            },
        shape = RoundedCornerShape(16.dp * (1f - progress)),
        backgroundColor = backgroundColor.lighten(0.4f * (1f - progress)),
        elevation = 1.dp + 23.dp * progress
    ) {
        BoxWithConstraints {
            Column(
                Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .offset(y = 256.dp * (1f - (progress - 0.5f).coerceAtLeast(0f) * 2))
                    .alpha((progress - 0.5f).coerceAtLeast(0f) * 2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(
                        Modifier.padding(8.dp),
                        MaterialTheme.colors.onSurface.copy(0.08f)
                    )
                    PlayController(Modifier.padding(16.dp))
                    ProgressBar(Modifier.padding(32.dp, 8.dp))
                    Row(modifier) {
                        IconButton({
                            CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                                lyricsState.animateTo(true)
                            }
                        }) {
                            Icon(Icons.Outlined.Article, "Lyrics")
                        }
                    }
                }
            }
            Box(
                Modifier.draggable(
                    rememberDraggableState {
                        horizontalDragOffset += it
                        with(density) {
                            when {
                                horizontalDragOffset <= -48.dp.toPx() -> player.next()
                                horizontalDragOffset >= 48.dp.toPx() -> player.previous()
                            }
                        }
                    },
                    Orientation.Horizontal,
                    onDragStopped = { horizontalDragOffset = 0f }
                ).padding(top = 12.dp)
            ) {
                Cover(
                    song,
                    Modifier
                        .padding(horizontal = 16.dp * (1f - progress))
                        .preferredSize((48.dp + 208.dp * progress * 2).coerceIn(256.dp * progress..256.dp))
                        .offset(80.dp * progress, 68.dp * progress)
                        .clip(SmoothRoundedCornerShape((5f - progress).toDouble()))
                        .clickable { player.playOrPause() }
                        .zIndex(1f)
                )
                Column(
                    Modifier.offset(80.dp + 16.dp * progress, 4.dp + 384.dp * progress)
                        .alpha((progress - 0.5f).absoluteValue * 2)
                ) {
                    Text(
                        song.title.toString(),
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = if (progress <= 0.5f) MaterialTheme.typography.body1
                        else MaterialTheme.typography.h6
                    )
                    Text(
                        song.subtitle.toString(),
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}