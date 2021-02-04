package com.kyant.pixelmusic.ui.nowplaying

import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.kyant.inimate.layer.progress
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.ui.component.Cover
import com.kyant.pixelmusic.ui.shape.SmoothRoundedCornerShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxWithConstraintsScope.NowPlayingCollapsed(
    state: SwipeableState<Boolean>,
    contentState: NowPlayingContentState,
    modifier: Modifier = Modifier
) {
    val player = LocalPixelPlayer.current
    val song = LocalNowPlaying.current
    val isLight = MaterialTheme.colors.isLight
    val progress = state.progress(constraints).coerceIn(0f..1f)
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

    if (progress <= 0.5f || (progress > 0.5f && contentState == NowPlayingContentState.SONG)) {
        Card(
            modifier
                .preferredSize(256.dp, 72.dp + (256.dp - 72.dp) * progress)
                .align(Alignment.TopCenter)
                .offset(y = 80.dp * progress),
            SmoothRoundedCornerShape((5f - progress).toDouble()),
            animateColorAsState(backgroundColor.copy(0.4f), tween(500)).value,
            elevation = 0.dp
        ) {
            Row(
                Modifier.fillMaxSize(),
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
                if (progress <= 0.5f) {
                    Column(
                        Modifier
                            .padding(end = 16.dp)
                            .alpha(1f - progress)
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