package com.kyant.pixelmusic.ui.component.nowplaying

import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.LocalePixelPlayer
import com.kyant.pixelmusic.ui.component.Cover
import com.kyant.pixelmusic.ui.shape.SmoothRoundedCornerShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Composable
fun BoxScope.NowPlayingCollapsed(
    state: NowPlayingState,
    contentState: NowPlayingContentState,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val player = LocalePixelPlayer.current
    val song = LocalNowPlaying.current
    val isLight = MaterialTheme.colors.isLight
    var backgroundColor by remember { mutableStateOf<Color?>(null) }
    val transition = updateTransition(state)
    val width by transition.animateDp({ spring(stiffness = 2000f) }) {
        when (it) {
            NowPlayingState.COLLAPSED -> 256.dp
            NowPlayingState.EXPANDED -> 256.dp
        }
    }
    val height by transition.animateDp {
        when (it) {
            NowPlayingState.COLLAPSED -> 72.dp
            NowPlayingState.EXPANDED -> 256.dp
        }
    }
    val offset by transition.animateIntOffset({ spring(0.8f, 800f) }) {
        with(density) {
            when (it) {
                NowPlayingState.COLLAPSED -> IntOffset.Zero
                NowPlayingState.EXPANDED -> IntOffset(0, 80.dp.roundToPx())
            }
        }
    }
    val cornerSize by transition.animateFloat {
        when (it) {
            NowPlayingState.COLLAPSED -> 5f
            NowPlayingState.EXPANDED -> 4f
        }
    }
    val alpha by transition.animateFloat({ tween(200, 50) }) {
        when (it) {
            NowPlayingState.COLLAPSED -> 0f
            NowPlayingState.EXPANDED -> 1f
        }
    }
    val coverSize by transition.animateDp {
        when (it) {
            NowPlayingState.COLLAPSED -> 48.dp
            NowPlayingState.EXPANDED -> 256.dp
        }
    }
    val horizontalPadding by transition.animateDp {
        when (it) {
            NowPlayingState.COLLAPSED -> 16.dp
            NowPlayingState.EXPANDED -> 0.dp
        }
    }

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

    if (state == NowPlayingState.COLLAPSED ||
        (state == NowPlayingState.EXPANDED && contentState == NowPlayingContentState.SONG)
    ) {
        Card(
            modifier
                .preferredSize(width, height)
                .align(Alignment.TopCenter)
                .offset { offset },
            SmoothRoundedCornerShape(cornerSize.toDouble()),
            animateColorAsState(
                backgroundColor?.copy(0.4f) ?: MaterialTheme.colors.surface,
                tween(500)
            ).value,
            elevation = 0.dp
        ) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Cover(
                    song,
                    Modifier
                        .padding(horizontal = horizontalPadding)
                        .preferredSize(coverSize)
                        .clip(SmoothRoundedCornerShape(cornerSize.toDouble()))
                        .clickable { player.playOrPause() }
                )
                if (state == NowPlayingState.COLLAPSED) {
                    Column(
                        Modifier
                            .padding(end = 16.dp)
                            .alpha(1 - alpha)
                    ) {
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