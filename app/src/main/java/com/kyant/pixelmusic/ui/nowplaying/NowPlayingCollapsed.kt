package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.inimate.layer.progress
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.ui.component.Cover
import com.kyant.pixelmusic.ui.shape.SmoothRoundedCornerShape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxWithConstraintsScope.NowPlayingCollapsed(
    state: SwipeableState<Boolean>,
    contentState: NowPlayingContentState,
    modifier: Modifier = Modifier
) {
    val player = LocalPixelPlayer.current
    val song = LocalNowPlaying.current
    val progress = state.progress(constraints).coerceIn(0f..1f)

    if (progress <= 0.5f || (progress > 0.5f && contentState == NowPlayingContentState.SONG)) {
        Row(
            modifier
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