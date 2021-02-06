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
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.kyant.inimate.layer.progress
import com.kyant.inimate.util.lighten
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.ui.component.ChipGroup
import com.kyant.pixelmusic.ui.component.StatefulProgressIndicator
import com.kyant.pixelmusic.ui.player.PlayController
import com.kyant.pixelmusic.ui.shape.SmoothRoundedCornerShape
import com.kyant.pixelmusic.ui.song.Cover
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

enum class NowPlayingContent { SONG, LYRICS, VISUALIZERS }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxWithConstraintsScope.NowPlaying(
    state: SwipeableState<Boolean>,
    playlistState: SwipeableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val player = LocalPixelPlayer.current
    val song = LocalNowPlaying.current
    val isLight = MaterialTheme.colors.isLight
    var contentState by remember { mutableStateOf(NowPlayingContent.SONG) }
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
            Column(
                Modifier.fillMaxSize()
                    .alpha(progress),
                Arrangement.SpaceBetween,
                Alignment.CenterHorizontally
            ) {
                Box {
                    when (contentState) {
                        NowPlayingContent.SONG -> {
                        }
                        NowPlayingContent.LYRICS -> {
                            Lyrics()
                        }
                        NowPlayingContent.VISUALIZERS -> {
                            Column {
                                Text(
                                    "Amplitudes",
                                    Modifier.padding(16.dp, 32.dp),
                                    style = MaterialTheme.typography.h6
                                )
                                AmplitudeVisualizer()
                            }
                        }
                    }
                }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(
                        Modifier.padding(8.dp),
                        MaterialTheme.colors.onSurface.copy(0.08f)
                    )
                    ChipGroup(
                        listOf(
                            Triple(
                                NowPlayingContent.SONG.name,
                                "Song",
                                Icons.Outlined.Audiotrack
                            ),
                            Triple(
                                NowPlayingContent.LYRICS.name,
                                "Lyrics",
                                Icons.Outlined.Article
                            ),
                            Triple(
                                NowPlayingContent.VISUALIZERS.name,
                                "Visualizers",
                                Icons.Outlined.Explore
                            )
                        ),
                        { contentState.name == it },
                        { contentState = NowPlayingContent.valueOf(it) },
                        Modifier.padding(16.dp)
                    )
                    PlayController(Modifier.padding(16.dp))
                    StatefulProgressIndicator(Modifier.padding(32.dp, 8.dp))
                }
            }
            if (progress <= 0.5f || (progress > 0.5f && contentState == NowPlayingContent.SONG)) {
                Row(
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
                    )
                        .preferredSize(256.dp, 72.dp + (256.dp - 72.dp) * progress)
                        .align(Alignment.TopCenter)
                        .offset(y = 80.dp * progress),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Cover(
                        song,
                        Modifier
                            .padding(horizontal = 16.dp * (1f - progress))
                            .preferredSize(48.dp + (256.dp - 48.dp) * progress)
                            .clip(SmoothRoundedCornerShape((5f - progress).toDouble()))
                            .clickable { player.playOrPause() }
                    )
                    Column(Modifier.padding(end = 16.dp)) {
                        Text(
                            song.title.toString(),
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = MaterialTheme.typography.body1
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
}